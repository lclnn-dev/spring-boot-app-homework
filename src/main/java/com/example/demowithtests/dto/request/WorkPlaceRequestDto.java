package com.example.demowithtests.dto.request;

import jakarta.validation.constraints.NotNull;

public record WorkPlaceRequestDto(

        @NotNull
        String name,

        Boolean airCondition,
        Boolean coffeeMachine) {

    public WorkPlaceRequestDto(String name, Boolean airCondition, Boolean coffeeMachine) {
        this.airCondition = airCondition != null ? airCondition : Boolean.TRUE;
        this.coffeeMachine = coffeeMachine != null ? coffeeMachine : Boolean.TRUE;
        this.name = name;
    }
}
