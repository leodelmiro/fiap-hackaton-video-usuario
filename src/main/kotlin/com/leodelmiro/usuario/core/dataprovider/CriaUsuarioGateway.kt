package com.leodelmiro.usuario.core.dataprovider

import com.leodelmiro.usuario.core.domain.Usuario

interface CriaUsuarioGateway {
    fun executar(usuario: Usuario): String
}