package com.Loop.Erands.DTO.AddressDto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddAddressDto {
    private UUID userId;
    private String street;
    private String city;
    private String state;
    private String zipCode;
}
