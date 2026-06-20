package com.concurrency.sandbox.dist_structures;

import java.util.HashMap;
import java.util.Map;

public class GCounter_CRDT {

    private final Map<String, Integer> counts = new HashMap<>();
    private final String nodeId;

    public GCounter_CRDT(String nodeId) {
        this.nodeId = nodeId;
        counts.put(nodeId, 0);
    }

    // local increment
    public void increment() {
        counts.put(nodeId, counts.getOrDefault(nodeId, 0) + 1);
    }

    // merge with another replica
    public void merge(Map<String, Integer> other) {
        for (Map.Entry<String, Integer> e : other.entrySet()) {
            String node = e.getKey();
            int value = e.getValue();

            counts.put(node,
                    Math.max(counts.getOrDefault(node, 0), value)
            );
        }
    }

    // total value
    public int value() {
        int sum = 0;
        for (int v : counts.values()) {
            sum += v;
        }
        return sum;
    }

    // expose state for replication
    public Map<String, Integer> state() {
        return new HashMap<>(counts);
    }
}
