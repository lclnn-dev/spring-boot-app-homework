package com.example.demowithtests.dto.response;

import lombok.Getter;
import lombok.Setter;

//@Accessors(chain = true)
@Getter
@Setter
public class AddressResponseDto {

    private Long id;
    private Boolean addressHasActive;
    private String country;
    private String city;
    private String street;
}
