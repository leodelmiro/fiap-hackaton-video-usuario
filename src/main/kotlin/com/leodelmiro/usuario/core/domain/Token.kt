package com.leodelmiro.usuario.core.domain

data class Token(var accessToken: String, var idToken: String, val tokenType: String = "Bearer", val expiresIn: Int)