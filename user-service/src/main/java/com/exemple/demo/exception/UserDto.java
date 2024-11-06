package com.exemple.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private Status status;
    private String token;

}