package com.leodelmiro.usuario.core.usecase

import com.leodelmiro.usuario.core.domain.Usuario

interface CriaUsuarioUseCase {
    fun executar(usuario: Usuario): Usuario
}