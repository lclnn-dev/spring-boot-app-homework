package com.example.demowithtests.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.Date;

//@Accessors(chain = true)
public class AddressDto {

    public Long id;
    public Boolean addressHasActive = Boolean.TRUE;
    public String country;
    public String city;
    public String street;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public Date date = Date.from(Instant.now());
}
