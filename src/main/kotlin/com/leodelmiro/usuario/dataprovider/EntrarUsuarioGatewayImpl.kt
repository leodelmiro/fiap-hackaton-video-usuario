package com.leodelmiro.usuario.dataprovider

import com.leodelmiro.usuario.core.dataprovider.EntrarUsuarioGateway
import com.leodelmiro.usuario.core.domain.Token
import com.leodelmiro.usuario.dataprovider.utils.CognitoUtils.calculateSecretHash
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthRequest


@Component
class EntrarUsuarioGatewayImpl(
    @Autowired
    private val cognitoClient: CognitoIdentityProviderClient,
    @Value("\${amazon.cognito.client-id}")
    private val clientId: String? = null,
    @Value("\${amazon.cognito.client-secret}")
    private val clientSecret: String? = null,
) : EntrarUsuarioGateway {
    override fun executar(usuario: String, senha: String): Token {
        val secretHash = calculateSecretHash(requireNotNull(clientId), requireNotNull(clientSecret), usuario)

        val initiateAuthRequest = InitiateAuthRequest.builder()
            .clientId(clientId)
            .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
            .authParameters(
                mapOf(
                    "USERNAME" to usuario,
                    "PASSWORD" to senha,
                    "SECRET_HASH" to secretHash
                )
            )
            .build()
        val initiateAuthResponse = cognitoClient.initiateAuth(initiateAuthRequest)
        val authenticationResult = initiateAuthResponse.authenticationResult()

        return Token(
            authenticationResult.accessToken(),
            authenticationResult.idToken(),
            authenticationResult.tokenType(),
            authenticationResult.expiresIn()
        )
    }
}