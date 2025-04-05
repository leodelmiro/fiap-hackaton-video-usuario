package com.leodelmiro.usuario.core.usercase.impl

import com.leodelmiro.usuario.core.dataprovider.CriaUsuarioGateway
import com.leodelmiro.usuario.core.domain.Usuario
import com.leodelmiro.usuario.core.usecase.impl.CriaUsuarioUseCaseImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import utils.criaUsuario

class CriaUsuarioUseCaseImplTest {

    private val criaUsuarioGateway: CriaUsuarioGateway = mock(CriaUsuarioGateway::class.java)
    private val criaUsuarioUseCase = CriaUsuarioUseCaseImpl(criaUsuarioGateway)

    @Test
    fun `deve criar um usuario e retornar o usuario com ID`() {
        val usuario = criaUsuario(id = "123")
        val idGerado = "123"
        `when`(criaUsuarioGateway.executar(usuario)).thenReturn("123")

        val resultado = criaUsuarioUseCase.executar(usuario)

        assertEquals(idGerado, resultado.id)
        assertEquals(usuario.usuario, resultado.usuario)
        assertEquals(usuario.senha, resultado.senha)
        assertEquals(usuario.email, resultado.email)
        verify(criaUsuarioGateway, times(1)).executar(usuario)
    }
}