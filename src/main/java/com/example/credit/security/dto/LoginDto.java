package com.example.credit.security.dto;

import jakarta.validation.constraints.NotNull;

/**
 * LoginDto
 */
public record LoginDto(@NotNull(message = "Username cannot be null") String username,@NotNull(message = "Password cannot be null") String password) {
}
