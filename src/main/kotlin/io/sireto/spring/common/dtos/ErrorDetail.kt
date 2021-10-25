package io.sireto.spring.common.dtos

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.*


@ApiModel(description = "Error Details to describe the exception occured during API invocation.")
data class ErrorDetail(
    @ApiModelProperty(value = "Time on which the exception occurred.", example = "2021-05-07T02:24:53.482+00:00")
    val timestamp: Date = Date(),
    @ApiModelProperty(value = "Error message.", example = "Missing parameter in the request.")
    val error: String? = null,
    @ApiModelProperty(value = "Detail of error message (if any)", example = "Every NFT must have a python code file associated that renders an output. It is required to upload it before minting the NFT.")
    val desc: String? = null,
    @ApiModelProperty(value = "Any object that can be relevant to the error associated. For example, remaining seconds to wait before making another request.", example = "40")
    val data: Any? = null,
    @ApiModelProperty(value = "Short code to determine what category of error it", example = "CARDANO_CLI_EXCEPTION")
    val code: String
)