package io.sireto.spring.common.exceptions

import io.sireto.spring.common.dtos.ErrorDetail
import io.sireto.spring.common.dtos.Response
import org.springframework.http.HttpStatus

abstract class BaseException(
        val httpStatus: HttpStatus,
        val code: String,
        val error: String?,
        val desc: String?,
        val data: Any?)
    : Exception(error) {
    fun asErrorResponse() = Response<Any>(error = ErrorDetail(error = error, desc = desc, data = data, code = code))
}