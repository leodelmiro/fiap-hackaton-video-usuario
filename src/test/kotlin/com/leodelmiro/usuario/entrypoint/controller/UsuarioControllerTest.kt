package com.leodelmiro.usuario.entrypoint.controller

import com.leodelmiro.usuario.core.domain.Token
import com.leodelmiro.usuario.core.usecase.CriaUsuarioUseCase
import com.leodelmiro.usuario.core.usecase.EntrarUsuarioUseCase
import com.leodelmiro.usuario.entrypoint.api.request.CriaUsuarioRequest
import com.leodelmiro.usuario.entrypoint.api.request.EntrarUsuarioRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import utils.criaUsuario

class UsuarioControllerTest {

    @Test
    fun `deve criar usuario com sucesso`() {
        val criaUsuarioRequest = CriaUsuarioRequest("teste", "test", "test@test.com")
        val criaUsuarioUseCase = mock(CriaUsuarioUseCase::class.java)
        val usuarioCriado = criaUsuario(id = "1")

        `when`(criaUsuarioUseCase.executar(criaUsuarioRequest.toUsuario())).thenReturn(usuarioCriado)

        val response = UsuarioController.criar(criaUsuarioRequest, criaUsuarioUseCase)

        assertEquals("1", response.id)
        assertEquals("teste", response.usuario)
        assertEquals("test", response.senha)
        assertEquals("test@test.com", response.email)
    }

    @Test
    fun `deve autenticar usuario com sucesso`() {
        val entrarUsuarioRequest = EntrarUsuarioRequest("usuario", "senha")
        val entrarUsuarioUseCase = mock(EntrarUsuarioUseCase::class.java)
        val tokenResponse = Token("accessToken", "Bearer", 3600)

        `when`(entrarUsuarioUseCase.executar("usuario", "senha")).thenReturn(tokenResponse)

        val response = UsuarioController.entrar(entrarUsuarioRequest, entrarUsuarioUseCase)

        assertEquals("accessToken", response.accessToken)
        assertEquals("Bearer", response.tokenType)
        assertEquals(3600, response.expiresIn)
    }
}