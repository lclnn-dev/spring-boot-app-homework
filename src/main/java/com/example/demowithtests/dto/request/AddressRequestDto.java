package com.example.demowithtests.dto.request;

import lombok.Getter;
import lombok.Setter;

//@Accessors(chain = true)
@Setter
@Getter
public class AddressRequestDto {

    private Boolean addressHasActive = Boolean.TRUE;
    private String country;
    private String city;
    private String street;
}
