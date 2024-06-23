package com.example.credit.security.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

/** SignupDto
 * @param name not null
 * @param password not null
 * @param confirmPass not null
 * @param agreeterms stores boolean not null   
 */
public record SignupDto(
        @NotNull(message = "Name cannot be empty") String name,
        @NotNull(message = "password cannot be empty") String password,
        String confirmPass,
        @NotNull(message = "Email cannot be null") @Email(message = "Enter valid email") String email,
        @NotNull(message = "Cannot proceed without agreeing") boolean agreeterms) {
}
