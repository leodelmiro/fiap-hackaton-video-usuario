package com.leodelmiro.usuario.core.dataprovider

import com.leodelmiro.usuario.core.domain.Usuario

interface BuscaEmailUsuarioGateway {
    fun executar(usuario: String): String
}