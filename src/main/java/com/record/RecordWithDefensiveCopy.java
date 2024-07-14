package com.record;

import java.util.List;

/**
 * Defensive copy of list in constructor and getter
 */
public record RecordWithDefensiveCopy(List<String> list) {

    public RecordWithDefensiveCopy(List<String> list) {
        this.list = List.copyOf(list);
    }

    public List<String> list() {
        return List.copyOf(list);
    }
    
}
