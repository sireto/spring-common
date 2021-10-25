package io.sireto.spring.common

import io.sireto.spring.common.components.JwtDecoder
import io.sireto.spring.common.components.JwtProperties
import io.sireto.spring.common.services.JwtSessionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import javax.servlet.http.HttpServletRequest

@SpringBootTest(classes = [JwtSessionService::class, JwtDecoder::class])
@ActiveProfiles("test")
@EnableConfigurationProperties(value = [JwtProperties::class])
class SpringStarterTest {
    @Autowired protected lateinit var jwtProperties: JwtProperties
    @Autowired protected lateinit var request: HttpServletRequest
    // dependency injection
    @Autowired protected lateinit var jwtDecoder: JwtDecoder
    @Autowired protected lateinit var jwtSessionService: JwtSessionService

}

