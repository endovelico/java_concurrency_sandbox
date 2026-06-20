package com.concurrency.sandbox.dist_structures;

import java.util.*;

public class VectorClock {

    private final Map<String, Integer> clock = new HashMap<>();
    private final String nodeId;

    public VectorClock(String nodeId) {
        this.nodeId = nodeId;
        clock.put(nodeId, 0);
    }

    // 1. Local event: increment own counter
    public void tick() {
        clock.put(nodeId, clock.getOrDefault(nodeId, 0) + 1);
    }

    // 2. Merge received clock into local clock
    public void merge(Map<String, Integer> other) {
        for (Map.Entry<String, Integer> entry : other.entrySet()) {
            String node = entry.getKey();
            int value = entry.getValue();

            clock.put(node,
                    Math.max(clock.getOrDefault(node, 0), value)
            );
        }

        // after merge, local node also advances
        tick();
    }

    // 3. Get snapshot (for sending)
    public Map<String, Integer> getSnapshot() {
        return new HashMap<>(clock);
    }
}