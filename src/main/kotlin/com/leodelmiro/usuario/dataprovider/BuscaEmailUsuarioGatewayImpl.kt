package com.leodelmiro.usuario.dataprovider

import com.leodelmiro.usuario.core.dataprovider.BuscaEmailUsuarioGateway
import com.leodelmiro.usuario.core.domain.Usuario
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserRequest
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType


@Component
class BuscaEmailUsuarioGatewayImpl(
    @Autowired
    private val cognitoClient: CognitoIdentityProviderClient,
    @Value("\${amazon.cognito.user-pool-id}")
    private val userPoolId: String? = null
) : BuscaEmailUsuarioGateway {
    override fun executar(usuario: String): String {
        val adminGetUserRequest = AdminGetUserRequest.builder()
            .userPoolId(userPoolId)
            .username(usuario)
            .build()
        val adminGetUserResponse = cognitoClient.adminGetUser(adminGetUserRequest)
        val email = adminGetUserResponse.userAttributes().stream()
            .filter { attr: AttributeType -> attr.name() == "email" }
            .findFirst()
            .map { obj: AttributeType -> obj.value() }
            .orElse("")
        return email
    }
}