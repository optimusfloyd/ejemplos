package com.ejemplo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioCreateDto(
        @Email @NotBlank String email,
        @NotBlank @Size(min = 6) String password
) {
}
