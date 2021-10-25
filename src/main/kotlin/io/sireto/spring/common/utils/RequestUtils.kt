package io.sireto.spring.common.utils

import io.sireto.spring.common.components.JwtProperties
import io.sireto.spring.common.exceptions.AuthException
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest

object RequestUtils {

    fun getRequest(throwExIfAbsent: Boolean = false): HttpServletRequest? {
        val attributes = RequestContextHolder.getRequestAttributes()
        if (attributes is NativeWebRequest) {
            return attributes.nativeRequest as HttpServletRequest
        }else if(attributes is ServletRequestAttributes){
            return attributes.request
        }
        if(throwExIfAbsent){
            throw AuthException("Failed to access authorized request.")
        }
        return null
    }

    fun getAuthTokenFromHeader(httpReq: HttpServletRequest? = getRequest(),
                               properties: JwtProperties,
                               throwExIfAbsent: Boolean = false): String?{
        val token = httpReq?.getHeader(properties.headerKey)?.replace("${properties.tokenPrefix} ", "")
        if (throwExIfAbsent && token.isNullOrBlank())
            throw AuthException("Authentication failed! No JWT token is found.")
        return token
    }
}