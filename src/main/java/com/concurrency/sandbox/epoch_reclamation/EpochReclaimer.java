/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.concurrency.sandbox.epoch_reclamation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public final class EpochReclaimer {

    private static final long INITIAL_EPOCH = 0;

    private final AtomicLong globalEpoch =
            new AtomicLong(INITIAL_EPOCH);

    private final ConcurrentLinkedQueue<Participant> participants =
            new ConcurrentLinkedQueue<>();

    private final ThreadLocal<Participant> localParticipant =
            ThreadLocal.withInitial(() -> {
                Participant p = new Participant();
                participants.add(p);
                return p;
            });

    /**
     * Enter an EBR protected region.
     */
    public Guard enter() {
        Participant p = localParticipant.get();

        /*
         * Publish our participation before accessing protected data.
         */
        for (;;) {
            long epoch = globalEpoch.get();

            p.epoch.set(epoch);
            p.active.set(true);

            /*
             * Make sure the epoch didn't change while we were
             * publishing our participation.
             */
            if (globalEpoch.get() == epoch) {
                return new Guard(p);
            }

            p.active.set(false);
        }
    }

    /**
     * Retire an object. It must no longer be reachable by the
     * concurrent data structure.
     */
    public void retire(Runnable reclamationAction) {
        Participant p = localParticipant.get();

        long epoch = globalEpoch.get();

        p.retired.add(new RetiredObject(
                epoch,
                reclamationAction
        ));

        /*
         * Occasionally try to advance/reclaim.
         */
        if (p.retired.size() >= 64) {
            collect();
        }
    }

    /**
     * Attempt to advance the global epoch and reclaim objects
     * that are known to be safe.
     */
    public void collect() {
        long current = globalEpoch.get();

        /*
         * Only advance if everybody that is active has
         * observed the current epoch.
         */
        boolean canAdvance = true;

        for (Participant p : participants) {
            if (p.active.get() && p.epoch.get() != current) {
                canAdvance = false;
                break;
            }
        }

        if (canAdvance) {
            globalEpoch.compareAndSet(current, current + 1);
        }

        long safeEpoch = minimumActiveEpoch();

        for (Participant p : participants) {
            p.reclaimUpTo(safeEpoch);
        }
    }

    private long minimumActiveEpoch() {
        long minimum = globalEpoch.get();

        for (Participant p : participants) {
            if (p.active.get()) {
                minimum = Math.min(
                        minimum,
                        p.epoch.get()
                );
            }
        }

        return minimum;
    }

    /**
     * Leave an EBR protected region.
     */
    private void leave(Participant participant) {
        participant.active.set(false);
    }

    public final class Guard implements AutoCloseable {
        private final Participant participant;
        private boolean closed;

        private Guard(Participant participant) {
            this.participant = participant;
        }

        @Override
        public void close() {
            if (!closed) {
                closed = true;
                leave(participant);
            }
        }
    }

    private static final class Participant {

        final AtomicBoolean active =
                new AtomicBoolean(false);

        final AtomicLong epoch =
                new AtomicLong(INITIAL_EPOCH);

        final List<RetiredObject> retired =
                new ArrayList<>();

        void reclaimUpTo(long safeEpoch) {
            for (int i = retired.size() - 1; i >= 0; i--) {
                RetiredObject object = retired.get(i);

                /*
                 * If the minimum active epoch is greater than
                 * the retirement epoch, no active reader can
                 * still belong to that old epoch.
                 */
                if (safeEpoch > object.retiredEpoch) {
                    retired.remove(i);
                    object.reclaim();
                }
            }
        }
    }

    private static final class RetiredObject {

        final long retiredEpoch;
        final Runnable reclamationAction;

        RetiredObject(
                long retiredEpoch,
                Runnable reclamationAction
        ) {
            this.retiredEpoch = retiredEpoch;
            this.reclamationAction = reclamationAction;
        }

        void reclaim() {
            reclamationAction.run();
        }
    }
}