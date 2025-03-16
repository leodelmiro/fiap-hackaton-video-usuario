package com.leodelmiro.usuario.entrypoint.api.request

import com.leodelmiro.usuario.core.domain.Usuario
import jakarta.validation.constraints.NotBlank

data class CriaUsuarioRequest(@NotBlank val usuario: String, @NotBlank val senha: String, @NotBlank val email: String) {
    fun toUsuario() = Usuario(usuario = this.usuario, senha = this.senha, email = this.email)
}