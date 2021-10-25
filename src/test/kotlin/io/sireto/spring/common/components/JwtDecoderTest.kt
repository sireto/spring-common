package io.sireto.spring.common.components

import io.sireto.spring.common.SpringStarterTest
import io.sireto.spring.common.exceptions.UnprocessableException
import io.sireto.spring.common.utils.StringUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import io.sireto.spring.common.utils.RequestSetupUtils.header
import io.sireto.spring.common.utils.RequestSetupUtils.jwtSignAlgo
import io.sireto.spring.common.utils.RequestSetupUtils.jwtToken
import io.sireto.spring.common.utils.RequestSetupUtils.payload

class JwtDecoderTest: SpringStarterTest() {

    @Test
    fun `Token string can be decoded into header and payload`(){
        val tokenDetails = jwtDecoder.decode(jwtToken)
        Assertions.assertEquals(StringUtils.mapToJson(header), tokenDetails.header)
        Assertions.assertEquals(StringUtils.mapToJson(payload), tokenDetails.payload)
    }

    @Test
    fun `Valid sign string from header returns enum SignatureAlgorithm`(){
        val chunkJwt = jwtDecoder.chunkTokenStr(jwtToken)
        val algorithm = jwtDecoder.getSignatureAlgo(chunkJwt.header)
        Assertions.assertEquals(jwtSignAlgo,algorithm)
    }

    @Test
    fun `Invalid sign string from header throws unprocessable exception`(){
        val exception = assertThrows<UnprocessableException> {
            jwtDecoder.getSignatureAlgo("ARandomHeader")
        }
        Assertions.assertEquals("Unable to parse algorithm from JWT header token.", exception.message)
    }

    @Test
    fun `Valid token string when verified returns true`(){
        val isValid = jwtDecoder.verify(jwtToken)
        Assertions.assertTrue(isValid)
    }

    @Test
    fun `Invalid token string when verified returns false`(){
        val exception = assertThrows<UnprocessableException> {
            jwtDecoder.verify("Invalid${jwtToken}StringHere")
        }
        Assertions.assertEquals("Unable to verify validity of token and signature.", exception.message)
    }
}