package com.leodelmiro.usuario.entrypoint.api.request

import jakarta.validation.constraints.NotBlank

data class EntrarUsuarioRequest(@NotBlank val usuario: String, @NotBlank val senha: String)