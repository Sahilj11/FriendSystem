package com.example.credit.security.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

/**
 * LoginDto
 *
 * @param email and password
 */
public record LoginDto(
        @Email(message = "Enter valid email") String email,
        @NotNull(message = "Password cannot be empty") String password) {
}
