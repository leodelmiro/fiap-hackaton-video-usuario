package com.leodelmiro.usuario.core.domain

data class Token(var accessToken: String, val tokenType: String = "Bearer", val expiresIn: Int)