package com.leodelmiro.usuario.core.usecase.impl

import com.leodelmiro.usuario.core.dataprovider.BuscaEmailUsuarioGateway
import com.leodelmiro.usuario.core.usecase.BuscaEmailUsuarioUseCase

class BuscaEmailUsuarioUseCaseImpl(val buscaEmailUsuarioGateway: BuscaEmailUsuarioGateway) : BuscaEmailUsuarioUseCase {
    override fun executar(usuario: String): String {
        return buscaEmailUsuarioGateway.executar(usuario)
    }
}