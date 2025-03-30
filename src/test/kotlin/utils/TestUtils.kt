package utils

import com.leodelmiro.usuario.core.domain.Usuario

fun criaUsuario(id: String? = null) = Usuario(id = id, usuario = "teste", senha = "test", email = "test@test.com")