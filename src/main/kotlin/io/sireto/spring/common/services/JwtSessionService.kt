package io.sireto.spring.common.services

import io.sireto.spring.common.components.JwtProperties
import io.sireto.spring.common.dtos.JwtUserDetails
import io.sireto.spring.common.exceptions.AuthException
import io.sireto.spring.common.exceptions.ForbiddenException
import io.sireto.spring.common.exceptions.UnprocessableException
import io.sireto.spring.common.utils.RequestUtils.getAuthTokenFromHeader
import io.sireto.spring.common.utils.RequestUtils.getRequest
import io.sireto.spring.common.utils.StringUtils.isUuid
import io.sireto.spring.common.utils.StringUtils.toUuidOrThrow400
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpServletRequest


@Service
class JwtSessionService @Autowired constructor(
    private val jwtProperties: JwtProperties,
    private val jwtDecoder: io.sireto.spring.common.components.JwtDecoder,
){

    fun getToken(httpReq: HttpServletRequest? = getRequest(), throwExIfAbsent: Boolean = true): String?{
        val token = getAuthTokenFromHeader(httpReq, jwtProperties)
        if (throwExIfAbsent && token.isNullOrBlank())
            throw AuthException("Authentication failed! No JWT token is found.")
        return token
    }

    fun getUser(httpReq: HttpServletRequest? = getRequest(), throwExIfAbsent: Boolean = false): JwtUserDetails?{
        val token = getToken(throwExIfAbsent = throwExIfAbsent)
        if(token.isNullOrBlank()){
            if(throwExIfAbsent)
                throw AuthException("Authentication failed! Please login before proceeding.")
            return null
        }
        return getUser(token)
    }

    fun getUser(token: String): JwtUserDetails?{
        val isValidToken = jwtDecoder.verify(token)
        try {
            if(isValidToken){
                val tokenDetails = jwtDecoder.decode(token)
                return jwtDecoder.getJwtUserDetails(tokenDetails)
            }
            throw Exception()
        }catch (ex: Exception){
            throw UnprocessableException("Failed to parse user details from JWT. ${ex.message}")
        }
    }

    fun hasAccess(userId: UUID){
        val user = getUser()
        if(user?.username.isNullOrBlank())
            throw AuthException("Authentication failed! Please login before proceeding.")
        if(userId != user?.userId?.toUuidOrThrow400() && user?.let { isJwtAdmin(it) } == false)
            throw ForbiddenException("Insufficient privilege to access the resource.")
    }

    fun hasAccess(username: String){
        val user = getUser()
        if(user?.username.isNullOrBlank())
            throw AuthException("Authentication failed! Please login before proceeding.")
        if(username != user?.username && user?.let { isJwtAdmin(it) } == false)
            throw ForbiddenException("Insufficient privilege to access the resource.")
    }

    fun hasAccess(userIdOrName: String, authRoles: List<String>){
        val user = getUser()
        val userRolesStr = user?.roles
        val rolesRequired = authRoles.isNotEmpty()
        val userRoles = userRolesStr?.split(",") ?: listOf()
        if(userIdOrName.isUuid()){
            hasAccess(userIdOrName.toUuidOrThrow400())
        } else{
            hasAccess(userIdOrName)
        }
        if(rolesRequired && userRoles.isEmpty())
            throw ForbiddenException("No privilege is assigned to the user $userIdOrName.")
        if(rolesRequired && !userRoles.containsAll(authRoles)){
            throw ForbiddenException("Insufficient privilege to access the resource.")
        }
    }

    fun isJwtAdmin(request: HttpServletRequest? = getRequest()) =
        getUser(request)?.let { isJwtAdmin(it) }

    fun isJwtAdmin(user: JwtUserDetails): Boolean{
        val matchName = user.username == jwtProperties.adminUser
        val matchUuid = user.userId == jwtProperties.adminUuid
        val matchRole = user.getRoles()?.contains("JWT_ADMIN") == true
        return matchName && matchUuid && matchRole
    }
}