package com.leodelmiro.usuario.dataprovider.utils

import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object CognitoUtils {
    fun calculateSecretHash(clientId: String, clientSecret: String, username: String): String {
        val signingKey = SecretKeySpec(clientSecret.toByteArray(), "HmacSHA256")
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(signingKey)
        val rawHash = mac.doFinal("$username$clientId".toByteArray())
        return Base64.getEncoder().encodeToString(rawHash)
    }
}