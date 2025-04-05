package com.leodelmiro.usuario.dataprovider

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient
import software.amazon.awssdk.services.cognitoidentityprovider.model.*

class EntrarUsuarioGatewayImplTest {

    private val cognitoClient: CognitoIdentityProviderClient = mock(CognitoIdentityProviderClient::class.java)
    private val clientId = "test-client-id"
    private val clientSecret = "test-client-secret"

    private val entrarUsuarioGateway = EntrarUsuarioGatewayImpl(
        cognitoClient = cognitoClient,
        clientId = clientId,
        clientSecret = clientSecret
    )

    @Test
    fun `deve retornar um Token valido quando as credenciais forem corretas`() {
        val usuario = "test-user"
        val senha = "test-password"

        val authenticationResult = AuthenticationResultType.builder()
            .accessToken("test-access-token")
            .idToken("test-id-token")
            .tokenType("Bearer")
            .expiresIn(3600)
            .build()

        val initiateAuthResponse = InitiateAuthResponse.builder()
            .authenticationResult(authenticationResult)
            .build()

        `when`(cognitoClient.initiateAuth(any(InitiateAuthRequest::class.java))).thenReturn(initiateAuthResponse)

        val token = entrarUsuarioGateway.executar(usuario, senha)

        assertEquals("test-access-token", token.accessToken)
        assertEquals("test-id-token", token.idToken)
        assertEquals("Bearer", token.tokenType)
        assertEquals(3600, token.expiresIn)
    }

    @Test
    fun `deve lancar excecao quando o Cognito retornar erro`() {
        val usuario = "test-user"
        val senha = "test-password"

        `when`(cognitoClient.initiateAuth(any(InitiateAuthRequest::class.java)))
            .thenThrow(CognitoIdentityProviderException.builder().message("Invalid credentials").build())

        val exception = assertThrows<CognitoIdentityProviderException> {
            entrarUsuarioGateway.executar(usuario, senha)
        }

        assertEquals("Invalid credentials", exception.message)
    }
}