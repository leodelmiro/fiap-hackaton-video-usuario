package com.leodelmiro.usuario.core.usecase.impl

import com.leodelmiro.usuario.core.dataprovider.EntrarUsuarioGateway
import com.leodelmiro.usuario.core.domain.Token
import com.leodelmiro.usuario.core.usecase.EntrarUsuarioUseCase

class EntrarUsuarioUseCaseImpl(val entrarUsuarioGateway: EntrarUsuarioGateway) : EntrarUsuarioUseCase {

    override fun executar(usuario: String, senha: String): Token = entrarUsuarioGateway.executar(usuario, senha)
}