package com.leodelmiro.usuario.core.usecase.impl

import com.leodelmiro.usuario.core.dataprovider.CriaUsuarioGateway
import com.leodelmiro.usuario.core.domain.Usuario
import com.leodelmiro.usuario.core.usecase.CriaUsuarioUseCase

class CriaUsuarioUseCaseImpl(val criaUsuarioGateway: CriaUsuarioGateway) : CriaUsuarioUseCase {
    override fun executar(usuario: Usuario): Usuario {
        usuario.id = criaUsuarioGateway.executar(usuario)
        return usuario
    }
}