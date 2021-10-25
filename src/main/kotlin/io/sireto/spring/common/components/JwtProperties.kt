package io.sireto.spring.common.components

import org.springframework.boot.context.properties.ConfigurationProperties
import java.math.BigInteger

@ConfigurationProperties("spring.jwt")
class JwtProperties {
    lateinit var secret: String
    lateinit var exp: BigInteger
    lateinit var tokenPrefix: String
    lateinit var headerKey: String
    lateinit var adminUser: String
    lateinit var adminUuid: String
    lateinit var signInUrl: String
    lateinit var signUpUrl: String
}