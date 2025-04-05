package com.leodelmiro.usuario.core.usercase.impl

import com.leodelmiro.usuario.core.dataprovider.EntrarUsuarioGateway
import com.leodelmiro.usuario.core.domain.Token
import com.leodelmiro.usuario.core.usecase.impl.EntrarUsuarioUseCaseImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class EntrarUsuarioUseCaseImplTest {

    private val entrarUsuarioGateway: EntrarUsuarioGateway = mock(EntrarUsuarioGateway::class.java)
    private val entrarUsuarioUseCase = EntrarUsuarioUseCaseImpl(entrarUsuarioGateway)

    @Test
    fun `deve retornar um token quando executar for chamado`() {
        val usuario = "usuario123"
        val senha = "senha123"
        val tokenEsperado = Token(accessToken = "token123", idToken = "token123", expiresIn = 300)
        `when`(entrarUsuarioGateway.executar(usuario, senha)).thenReturn(tokenEsperado)

        val resultado = entrarUsuarioUseCase.executar(usuario, senha)

        assertEquals(tokenEsperado, resultado)
        verify(entrarUsuarioGateway, times(1)).executar(usuario, senha)
    }
}