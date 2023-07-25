package com.example.demowithtests.util;

import com.example.demowithtests.enumeration.WorkPassAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryWorkPassActions {

    private static final Map<Key, List<WorkPassAction>> history = new HashMap<>();

    private HistoryWorkPassActions() {
    }

    public record Key(int employeeId, long passId) {
    }

    public static void addAction(WorkPassAction action, int employeeId, long passId) {
        Key key = new Key(employeeId, passId);
        history.computeIfAbsent(key, k -> new ArrayList<>()).add(action);
    }

    public static Map<Key, List<WorkPassAction>> getHistory() {
        return history;
    }
}
