package com.leodelmiro.usuario.dataprovider

import com.leodelmiro.usuario.core.dataprovider.CriaUsuarioGateway
import com.leodelmiro.usuario.core.domain.Usuario
import com.leodelmiro.usuario.dataprovider.utils.CognitoUtils.calculateSecretHash
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminConfirmSignUpRequest
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpRequest
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


@Component
class CriaUsuarioGatewayImpl(
    @Autowired
    private val cognitoClient: CognitoIdentityProviderClient,
    @Value("\${amazon.cognito.client-id}")
    private val clientId: String? = null,
    @Value("\${amazon.cognito.client-secret}")
    private val clientSecret: String? = null,
    @Value("\${amazon.cognito.user-pool-id}")
    private val userPoolId: String? = null
) : CriaUsuarioGateway {
    override fun executar(usuario: Usuario): String {
        val secretHash = calculateSecretHash(requireNotNull(clientId), requireNotNull(clientSecret), usuario.usuario)

        val signUpRequest = SignUpRequest.builder()
            .clientId(clientId)
            .username(usuario.usuario)
            .password(usuario.senha)
            .secretHash(secretHash)
            .userAttributes(
                AttributeType.builder().name("email").value(usuario.email).build()
            )
            .build()
        val userSub = cognitoClient.signUp(signUpRequest).userSub()

        val adminConfirmSignUpRequest = AdminConfirmSignUpRequest.builder()
            .userPoolId(userPoolId)
            .username(usuario.usuario)
            .build()
        cognitoClient.adminConfirmSignUp(adminConfirmSignUpRequest)

        return userSub
    }

}