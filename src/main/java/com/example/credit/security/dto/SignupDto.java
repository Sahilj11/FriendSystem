package com.example.credit.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

/** SignupDto
 * @param name 
 * @param password 
 * @param confirmPass 
 * @param agreeterms stores boolean
 */
public record SignupDto(
        @NotNull(message = "Name cannot be empty") String name,
        @NotNull(message = "password cannot be empty") String password,
        String confirmPass,
        @Email(message = "Enter valid email") String email,
        @NotNull(message = "Cannot proceed without agreeing") boolean agreeterms) {
}
