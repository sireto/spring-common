package io.sireto.spring.common.service

import io.sireto.spring.common.SpringStarterTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import io.sireto.spring.common.utils.RequestSetupUtils.invalidJwtToken
import io.sireto.spring.common.utils.RequestSetupUtils.jwtPayloadUser
import io.sireto.spring.common.utils.RequestSetupUtils.jwtToken

class JwtSessionTest: SpringStarterTest() {

    @Test
    fun `Returns a user details with userId, username and roles when passing a valid JWT`(){
        val jwtUser = jwtSessionService.getUser(jwtToken)
        Assertions.assertNotNull(jwtUser)
        Assertions.assertEquals(jwtPayloadUser.userId, jwtUser!!.userId)
        Assertions.assertEquals(jwtPayloadUser.username, jwtUser.username)
        Assertions.assertEquals(jwtPayloadUser.roles, jwtUser.roles)
        Assertions.assertEquals(jwtPayloadUser.expiryAt, jwtUser.expiryAt)
    }

    @Test
    fun `Returns null when getUser by passing invalid JWT`(){
        val jwtUser = jwtSessionService.getUser(invalidJwtToken)
        Assertions.assertNull(jwtUser)
    }
}