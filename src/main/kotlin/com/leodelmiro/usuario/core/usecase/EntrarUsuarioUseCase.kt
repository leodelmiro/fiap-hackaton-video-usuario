package com.leodelmiro.usuario.core.usecase

import com.leodelmiro.usuario.core.domain.Token

interface EntrarUsuarioUseCase {
    fun executar(usuario: String, senha: String): Token
}