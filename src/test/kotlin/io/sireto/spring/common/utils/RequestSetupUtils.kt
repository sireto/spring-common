package io.sireto.spring.common.utils

import io.jsonwebtoken.SignatureAlgorithm
import io.sireto.spring.common.dtos.JwtUserDetails
import java.math.BigInteger

object RequestSetupUtils {

    val jwtSignAlgo: SignatureAlgorithm = SignatureAlgorithm.HS256
    val jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJjYzFlMTQ0My0wYmIxLTQ2NGItOTZiNy04NGFjOTRhODY1NzEiLCJ1c2VybmFtZSI6ImFydGlzdCIsInJvbGVzIjoiQVJUSVNULEtZQ19WRVJJRklFRCIsImV4cCI6MTY0MDgwMTcwMH0.vDLbxlL3jTV2Z76W0mKjos22gAwlNk7HN66BX76uycY"
    val invalidJwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
    val header = mapOf(Pair("alg", "HS256"), Pair("typ", "JWT"))
    val payload = mapOf(
        Pair("sub", "cc1e1443-0bb1-464b-96b7-84ac94a86571"),
        Pair("username", "artist"),
        Pair("roles", "ARTIST,KYC_VERIFIED"),
        Pair("exp", 1640801700),

    )
    val jwtPayloadUser = JwtUserDetails(
        username = "artist",
        userId = "cc1e1443-0bb1-464b-96b7-84ac94a86571",
        roles = "ARTIST,KYC_VERIFIED",
        expiryAt = BigInteger.valueOf(1640801700)
    )
}