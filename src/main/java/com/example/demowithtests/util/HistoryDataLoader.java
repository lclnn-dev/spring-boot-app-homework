package com.example.demowithtests.util;

import com.example.demowithtests.enumeration.WorkPassAction;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class HistoryDataLoader {

    @Value("${history-data.work-pass}")
    private String filePath;

    @PostConstruct
    public void loadHistoryData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int employeeId = Integer.parseInt(parts[0]);
                long passId = Long.parseLong(parts[1]);
                WorkPassAction action = WorkPassAction.valueOf(parts[2].toUpperCase());

                HistoryWorkPassActions.addAction(action, employeeId, passId);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file.");
        }
    }

    @PreDestroy
    public void saveHistoryData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            Map<HistoryWorkPassActions.Key, List<WorkPassAction>> historyMap = HistoryWorkPassActions.getHistory();
            for (Map.Entry<HistoryWorkPassActions.Key, List<WorkPassAction>> entry : historyMap.entrySet()) {
                HistoryWorkPassActions.Key key = entry.getKey();
                List<WorkPassAction> actions = entry.getValue();

                for (WorkPassAction action : actions) {
                    String line = key.employeeId() + "," + key.passId() + "," + action;
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing file.");
        }
    }
}