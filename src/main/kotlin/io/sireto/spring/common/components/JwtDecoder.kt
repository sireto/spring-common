package io.sireto.spring.common.components

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator
import io.sireto.spring.common.dtos.JwtUserDetails
import io.sireto.spring.common.exceptions.UnprocessableException
import io.sireto.spring.common.utils.DateUtils
import io.sireto.spring.common.utils.StringUtils
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.spec.SecretKeySpec

data class TokenDetails(
    val header: String,
    val payload: String,
    val sign: String
)

@Component
class JwtDecoder(
    private val jwtProperties: io.sireto.spring.common.components.JwtProperties
) {

    private val decoder = Base64.getUrlDecoder()

    fun chunkTokenStr(token: String): io.sireto.spring.common.components.TokenDetails {
        val chunks = token.split(".")
        try {
            return io.sireto.spring.common.components.TokenDetails(
                header = chunks[0],
                payload = chunks[1],
                sign = chunks[2]
            )
        }catch (ex: Exception){
            throw UnprocessableException("Unable to parse JWT structure.")
        }
    }

    fun decode(token: String): io.sireto.spring.common.components.TokenDetails {
        val chunkedToken = chunkTokenStr(token)
        try {
            return io.sireto.spring.common.components.TokenDetails(
                header = decoder.decode(chunkedToken.header).decodeToString(),
                payload = decoder.decode(chunkedToken.payload).decodeToString(),
                sign = decoder.decode(chunkedToken.sign).decodeToString()
            )
        }catch (ex: Exception){
            throw UnprocessableException("Unable to decode JWT structure. ${ex.message}")
        }
    }

    fun getSignatureAlgo(chunkedHeader: String): SignatureAlgorithm{
        try {
            val decodedHeader = decoder.decode(chunkedHeader).decodeToString()
            val mapHeader = StringUtils.jsonToMap(decodedHeader)
            val algoStr = mapHeader["alg"] as String
            return SignatureAlgorithm.valueOf(algoStr)
        }catch (ex: Exception){
            throw UnprocessableException("Unable to parse algorithm from JWT header token.")
        }
    }

    fun verify(token: String, secret: String = jwtProperties.secret): Boolean {
        return verify(chunkTokenStr(token), secret) && validExpJwt(decode(token))
    }

    fun getJwtUserDetails(tokenDetails: io.sireto.spring.common.components.TokenDetails): JwtUserDetails{
        val payloadMap = StringUtils.jsonToMap(tokenDetails.payload)
        return JwtUserDetails(
            userId = payloadMap["sub"] as String?,
            username = payloadMap["username"] as String?,
            roles = payloadMap["roles"] as String?,
            expiryAt = (payloadMap["exp"] as Double).toBigDecimal().toBigInteger()
        )
    }

    fun getJwt(uuid: UUID, username: String, roles: String): String {
        val expiryInSec =  DateUtils.addMin(Date(), jwtProperties.exp.toInt()).time / 1000
        val token = JWT.create()
            .withSubject(uuid.toString())
            .withClaim("exp", expiryInSec)
            .withClaim("username", username)
            .withClaim("roles", roles)
            .sign(Algorithm.HMAC256(jwtProperties.secret.toByteArray()))
        return token
    }

    /*********************** internal helpers ***************************/

    private fun verify(chunkedToken: io.sireto.spring.common.components.TokenDetails, secret: String): Boolean{
        try {
            val signatureAlgo = getSignatureAlgo(chunkedToken.header)
            val secretKeySpec = SecretKeySpec(secret.toByteArray(), signatureAlgo.jcaName)
            val tokenWithoutSignature = "${chunkedToken.header}.${chunkedToken.payload}"
            val signature = chunkedToken.sign
            val validator = DefaultJwtSignatureValidator(signatureAlgo, secretKeySpec)
            return validator.isValid(tokenWithoutSignature, signature)
        }catch (ex: Exception){
            throw UnprocessableException("Unable to verify validity of token and signature.")
        }
    }

    private fun validExpJwt(tokenDetails: io.sireto.spring.common.components.TokenDetails): Boolean{
        return try {
            val now = Date()
            val tokenExpiresAt = DateUtils.date(
                getJwtUserDetails(tokenDetails).expiryAt.toLong() * 1000)
            now.before(tokenExpiresAt)
        }catch (ex: Exception){
            false
        }
    }
}