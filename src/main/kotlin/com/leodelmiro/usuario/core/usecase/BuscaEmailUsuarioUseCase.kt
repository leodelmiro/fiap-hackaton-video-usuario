package com.leodelmiro.usuario.core.usecase

interface BuscaEmailUsuarioUseCase {
    fun executar(usuario: String): String
}