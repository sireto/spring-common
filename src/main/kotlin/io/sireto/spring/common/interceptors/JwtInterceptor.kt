package io.sireto.spring.common.interceptors

import io.sireto.spring.common.components.JwtProperties
import io.sireto.spring.common.services.JwtSessionService
import io.sireto.spring.common.utils.RequestUtils.getAuthTokenFromHeader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class JwtInterceptor @Autowired constructor(
    private val jwtProperties: JwtProperties,
    private val sessionService: JwtSessionService,
): HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val isJwtAdmin = checkIfJwtTokenBasedReqIsIntercepted(request)
        if (!isJwtAdmin){
            val token = getAuthTokenFromHeader(request, jwtProperties)
            if(!token.isNullOrBlank()){
                response.addHeader(jwtProperties.headerKey, "${jwtProperties.tokenPrefix} $token")
            }
        }
        return true
    }

    // IMPORTANT: jwt admin based token is for one way communication between microservices
    // it should not be shared via response
    // it is added to the request
    fun checkIfJwtTokenBasedReqIsIntercepted(
        request: HttpServletRequest
    ): Boolean{
        val user = sessionService.isJwtAdmin(request)
        return user == true
    }
}