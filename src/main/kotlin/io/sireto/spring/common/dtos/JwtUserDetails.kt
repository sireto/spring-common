package io.sireto.spring.common.dtos

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.math.BigInteger

@ApiModel(value="User details obtained from jwt.")
open class JwtUserDetails (

    @ApiModelProperty(value="User uuid.")
    val userId: String? = null,

    @ApiModelProperty(value="User name.")
    val username: String? = null,

    @ApiModelProperty(value="User associated roles.")
    val roles: String? = null,

    @ApiModelProperty(value="User token expiry in milliseconds")
    val expiryAt: BigInteger = BigInteger.ZERO
){
    fun getRoles() = roles?.split(",")
}