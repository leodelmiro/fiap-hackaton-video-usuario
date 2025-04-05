package com.leodelmiro.usuario.dataprovider

import com.leodelmiro.usuario.core.domain.Usuario
import com.leodelmiro.usuario.dataprovider.utils.CognitoUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient
import software.amazon.awssdk.services.cognitoidentityprovider.model.*

class CriaUsuarioGatewayImplTest {

    private val cognitoClient: CognitoIdentityProviderClient = mock(CognitoIdentityProviderClient::class.java)
    private val clientId = "test-client-id"
    private val clientSecret = "test-client-secret"
    private val userPoolId = "test-user-pool-id"

    private val criaUsuarioGateway = CriaUsuarioGatewayImpl(
        cognitoClient = cognitoClient,
        clientId = clientId,
        clientSecret = clientSecret,
        userPoolId = userPoolId
    )

    @Test
    fun `deve criar usuario e retornar userSub quando os dados forem validos`() {
        val usuario = Usuario("test-user", "test-password", "senha", "test-email@example.com")

        val signUpResponse = SignUpResponse.builder()
            .userSub("test-user-sub")
            .build()

        `when`(cognitoClient.signUp(any(SignUpRequest::class.java))).thenReturn(signUpResponse)

        val userSub = criaUsuarioGateway.executar(usuario)

        assertEquals("test-user-sub", userSub)
    }

    @Test
    fun `deve lancar excecao quando o Cognito retornar erro no signUp`() {
        val usuario = Usuario("test-user", "test-password", "senha", "test-email@example.com")

        `when`(cognitoClient.signUp(any(SignUpRequest::class.java)))
            .thenThrow(CognitoIdentityProviderException.builder().message("SignUp error").build())

        val exception = assertThrows<CognitoIdentityProviderException> {
            criaUsuarioGateway.executar(usuario)
        }
        assertEquals("SignUp error", exception.message)
    }

    @Test
    fun `deve lancar excecao quando o Cognito retornar erro no adminConfirmSignUp`() {
        val usuario = Usuario("test-user", "test-password", "senha", "test-email@example.com")
        val signUpResponse = SignUpResponse.builder()
            .userSub("test-user-sub")
            .build()

        `when`(cognitoClient.signUp(any(SignUpRequest::class.java))).thenReturn(signUpResponse)
        `when`(cognitoClient.adminConfirmSignUp(any(AdminConfirmSignUpRequest::class.java)))
            .thenThrow(CognitoIdentityProviderException.builder().message("AdminConfirmSignUp error").build())

        val exception = assertThrows<CognitoIdentityProviderException> {
            criaUsuarioGateway.executar(usuario)
        }
        assertEquals("AdminConfirmSignUp error", exception.message)
    }
}