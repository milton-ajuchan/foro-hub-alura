package com.alura.foro_api.infra.security;

import jakarta.validation.constraints.NotBlank;

public record JWTtokenDTO(@NotBlank(message = "El token JWT no puede estar vacío.") String JWTtoken) {
}
