package com.leodelmiro.usuario.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient
import java.net.URI


@Configuration
class CognitoConfig {
    @Value("\${amazon.cognito.url}")
    private val endpoint: String? = null

    @Value("\${spring.cloud.aws.credentials.access-key}")
    private val accessKey: String? = null

    @Value("\${spring.cloud.aws.credentials.secret-key}")
    private val secretKey: String? = null

    @Value("\${spring.cloud.aws.credentials.session}")
    private val sessionToken: String? = null

    @Value("\${spring.cloud.aws.region.static}")
    private val region: String? = null

    @Bean
    fun cognitoClient(): CognitoIdentityProviderClient = CognitoIdentityProviderClient.builder()
        .region(Region.of(region))
        .endpointOverride(URI.create(requireNotNull(endpoint)))
        .credentialsProvider { AwsSessionCredentials.create(accessKey, secretKey, sessionToken) }
        .build()

}