package io.sireto.spring.common.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.*

@ApiModel(description = "A wrapper class for response of endpoint. Holds common values and maintain a uniformity.")
data class Response<T>(
    @ApiModelProperty(value = "Any class that relevant to the endpoint")
    var payload: T? = null,

    @ApiModelProperty(value = "Detail of error message if exception occurred. Else, null.")
    var error: ErrorDetail? = null,

    @JsonProperty("api_version")
    @ApiModelProperty(value = "API version.")
    val apiVersion: String = "v1.0",

    @JsonProperty("response_time")
    @ApiModelProperty("Time on which response was generated on server.")
    val responseTime: Date = Date()

)