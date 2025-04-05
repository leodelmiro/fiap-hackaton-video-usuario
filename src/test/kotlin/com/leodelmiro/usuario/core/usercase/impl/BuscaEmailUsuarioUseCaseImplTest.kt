package com.leodelmiro.usuario.core.usercase.impl

import com.leodelmiro.usuario.core.dataprovider.BuscaEmailUsuarioGateway
import com.leodelmiro.usuario.core.usecase.impl.BuscaEmailUsuarioUseCaseImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class BuscaEmailUsuarioUseCaseImplTest {

    private val buscaEmailUsuarioGateway: BuscaEmailUsuarioGateway = mock(BuscaEmailUsuarioGateway::class.java)
    private val buscaEmailUsuarioUseCase = BuscaEmailUsuarioUseCaseImpl(buscaEmailUsuarioGateway)

    @Test
    fun `deve retornar o email do usuario quando executar for chamado`() {
        val usuario = "usuario123"
        val emailEsperado = "usuario123@email.com"
        `when`(buscaEmailUsuarioGateway.executar(usuario)).thenReturn(emailEsperado)

        val resultado = buscaEmailUsuarioUseCase.executar(usuario)

        assertEquals(emailEsperado, resultado)
        verify(buscaEmailUsuarioGateway, times(1)).executar(usuario)
    }
}