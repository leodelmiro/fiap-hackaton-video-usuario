package com.leodelmiro.usuario.entrypoint.controller

import com.leodelmiro.usuario.core.usecase.CriaUsuarioUseCase
import com.leodelmiro.usuario.core.usecase.EntrarUsuarioUseCase
import com.leodelmiro.usuario.entrypoint.api.request.CriaUsuarioRequest
import com.leodelmiro.usuario.entrypoint.api.request.EntrarUsuarioRequest
import com.leodelmiro.usuario.entrypoint.api.response.CriaUsuarioResponse
import com.leodelmiro.usuario.entrypoint.api.response.TokenResponse

object UsuarioController {

    fun criar(
        criaUsuarioRequest: CriaUsuarioRequest,
        criaUsuarioUseCase: CriaUsuarioUseCase
    ): CriaUsuarioResponse = criaUsuarioRequest.toUsuario().let {
        criaUsuarioUseCase.executar(it)
    }.let {
        CriaUsuarioResponse(it.id ?: "", it.usuario, it.senha, it.email)
    }

    fun entrar(
        entrarUsuarioRequest: EntrarUsuarioRequest,
        entrarUsuarioUseCase: EntrarUsuarioUseCase
    ) = entrarUsuarioUseCase.executar(entrarUsuarioRequest.usuario, entrarUsuarioRequest.senha).let {
        TokenResponse(it.accessToken, it.tokenType, it.expiresIn)
    }
}
