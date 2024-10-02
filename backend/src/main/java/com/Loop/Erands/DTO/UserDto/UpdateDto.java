package com.Loop.Erands.DTO.UserDto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateDto {
    private UUID toUpdateUserId;
    private String firstName;
    private String lastName;
    private String password;

    UpdateDto(){
        firstName = null;
        lastName = null;
        password = null;
    }
}
