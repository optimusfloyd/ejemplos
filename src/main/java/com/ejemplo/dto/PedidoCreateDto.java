package com.ejemplo.dto;

import jakarta.validation.constraints.NotBlank;

public record PedidoCreateDto(@NotBlank String descripcion) {
}
