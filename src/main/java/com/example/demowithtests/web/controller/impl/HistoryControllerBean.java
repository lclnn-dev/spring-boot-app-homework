package com.example.demowithtests.web.controller.impl;

import com.example.demowithtests.dto.response.HistoryPassResponseDto;
import com.example.demowithtests.enumeration.WorkPassAction;
import com.example.demowithtests.util.HistoryWorkPassActions;
import com.example.demowithtests.util.mapper.HistoryPassMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/history")
@AllArgsConstructor
public class HistoryControllerBean {

    private final HistoryPassMapper historyPassMapper;

    @GetMapping("/passes")
    public List<HistoryPassResponseDto> getAllPassHistory() {
        Map<HistoryWorkPassActions.Key, List<WorkPassAction>> historyMap = HistoryWorkPassActions.getHistory();
        List<HistoryPassResponseDto> historyDtoList = new ArrayList<>();

        for (Map.Entry<HistoryWorkPassActions.Key, List<WorkPassAction>> entry : historyMap.entrySet()) {
            HistoryWorkPassActions.Key key = entry.getKey();
            List<WorkPassAction> actions = entry.getValue();

            for (WorkPassAction action : actions) {
                HistoryPassResponseDto historyDto = historyPassMapper.toHistoryPassResponse(key, action);
                historyDtoList.add(historyDto);
            }
        }
        return historyDtoList;
    }
}
