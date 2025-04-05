package com.leodelmiro.usuario.core.dataprovider

import com.leodelmiro.usuario.core.domain.Token

interface EntrarUsuarioGateway {
    fun executar(usuario: String, senha: String): Token
}