package com.leodelmiro.usuario.entrypoint.api

import com.leodelmiro.usuario.core.domain.Token
import com.leodelmiro.usuario.core.domain.Usuario
import com.leodelmiro.usuario.core.usecase.BuscaEmailUsuarioUseCase
import com.leodelmiro.usuario.core.usecase.CriaUsuarioUseCase
import com.leodelmiro.usuario.core.usecase.EntrarUsuarioUseCase
import com.leodelmiro.usuario.entrypoint.api.request.CriaUsuarioRequest
import com.leodelmiro.usuario.entrypoint.api.request.EntrarUsuarioRequest
import com.leodelmiro.usuario.entrypoint.api.response.BuscaEmailUsuarioResponse
import com.leodelmiro.usuario.entrypoint.api.response.CriaUsuarioResponse
import com.leodelmiro.usuario.entrypoint.api.response.TokenResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

class UsuarioApiTest {

    private val criaUsuarioUseCase = mock(CriaUsuarioUseCase::class.java)
    private val buscaEmailUsuarioUseCase = mock(BuscaEmailUsuarioUseCase::class.java)
    private val entrarUsuarioUseCase = mock(EntrarUsuarioUseCase::class.java)

    private val usuarioApi = UsuarioApi(criaUsuarioUseCase, buscaEmailUsuarioUseCase, entrarUsuarioUseCase)

    @BeforeEach
    fun setup() {
        val mockRequest = MockHttpServletRequest()
        mockRequest.requestURI = "/api/v1/usuarios"
        RequestContextHolder.setRequestAttributes(ServletRequestAttributes(mockRequest))
    }

    @Test
    fun `deve criar usuario com sucesso`() {
        val criaUsuarioRequest = CriaUsuarioRequest("usuario", "senha", "test@test.com")
        val usuarioCriado = Usuario("1", "usuario", "senha", "test@test.com")
        `when`(criaUsuarioUseCase.executar(criaUsuarioRequest.toUsuario())).thenReturn(usuarioCriado)

        val response: ResponseEntity<CriaUsuarioResponse> = usuarioApi.cria(criaUsuarioRequest)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals("1", response.body?.id)
        assertEquals("usuario", response.body?.usuario)
        assertEquals("test@test.com", response.body?.email)
        verify(criaUsuarioUseCase, times(1)).executar(criaUsuarioRequest.toUsuario())
    }

    @Test
    fun `deve buscar email do usuario com sucesso`() {
        val usuario = "usuario"
        val email = "email@test.com"
        `when`(buscaEmailUsuarioUseCase.executar(usuario)).thenReturn(email)

        val response: ResponseEntity<BuscaEmailUsuarioResponse> = usuarioApi.buscarEmail(usuario)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(usuario, response.body?.usuario)
        assertEquals(email, response.body?.email)
        verify(buscaEmailUsuarioUseCase, times(1)).executar(usuario)
    }

    @Test
    fun `deve autenticar usuario com sucesso`() {
        val entrarUsuarioRequest = EntrarUsuarioRequest("usuario", "senha")
        val token = Token("accessToken", "idToken", "Bearer", 3600)
        `when`(entrarUsuarioUseCase.executar("usuario", "senha")).thenReturn(token)

        val response: ResponseEntity<TokenResponse> = usuarioApi.entrar(entrarUsuarioRequest)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("accessToken", response.body?.accessToken)
        assertEquals("Bearer", response.body?.tokenType)
        assertEquals(3600, response.body?.expiresIn)
        verify(entrarUsuarioUseCase, times(1)).executar("usuario", "senha")
    }
}