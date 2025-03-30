package com.leodelmiro.usuario.dataprovider

import com.leodelmiro.usuario.core.dataprovider.BuscaEmailUsuarioGateway
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserRequest
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserResponse
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType

class BuscaEmailUsuarioGatewayImplTest {

    private val cognitoClient: CognitoIdentityProviderClient = mock(CognitoIdentityProviderClient::class.java)
    private val userPoolId = "test-user-pool-id"
    private val buscaEmailUsuarioGateway: BuscaEmailUsuarioGateway = BuscaEmailUsuarioGatewayImpl(cognitoClient, userPoolId)

    @Test
    fun `deve retornar email do usuario com sucesso`() {
        val usuario = "test-username"
        val emailEsperado = "test@example.com"

        val adminGetUserRequest = AdminGetUserRequest.builder()
            .userPoolId(userPoolId)
            .username(usuario)
            .build()

        val userAttributes = listOf(
            AttributeType.builder().name("email").value(emailEsperado).build(),
            AttributeType.builder().name("name").value("Test User").build()
        )

        val adminGetUserResponse = AdminGetUserResponse.builder()
            .userAttributes(userAttributes)
            .build()

        `when`(cognitoClient.adminGetUser(adminGetUserRequest)).thenReturn(adminGetUserResponse)

        val email = buscaEmailUsuarioGateway.executar(usuario)

        assertEquals(emailEsperado, email)
        verify(cognitoClient, times(1)).adminGetUser(adminGetUserRequest)
    }

    @Test
    fun `deve retornar string vazia quando email nao for encontrado`() {
        val usuario = "test-username"

        val adminGetUserRequest = AdminGetUserRequest.builder()
            .userPoolId(userPoolId)
            .username(usuario)
            .build()

        val userAttributes = listOf(
            AttributeType.builder().name("name").value("Test User").build()
        )

        val adminGetUserResponse = AdminGetUserResponse.builder()
            .userAttributes(userAttributes)
            .build()

        `when`(cognitoClient.adminGetUser(adminGetUserRequest)).thenReturn(adminGetUserResponse)

        val email = buscaEmailUsuarioGateway.executar(usuario)

        assertEquals("", email)
        verify(cognitoClient, times(1)).adminGetUser(adminGetUserRequest)
    }
}