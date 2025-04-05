package com.leodelmiro.usuario.entrypoint.api.response

import com.leodelmiro.usuario.core.domain.Token


data class TokenResponse(var accessToken: String, val tokenType: String, val expiresIn: Int) {
    fun from(token: Token) = TokenResponse(token.accessToken, token.tokenType, token.expiresIn)
}