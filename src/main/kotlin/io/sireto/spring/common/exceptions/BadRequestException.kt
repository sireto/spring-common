package io.sireto.spring.common.exceptions

import org.springframework.http.HttpStatus

class BadRequestException(
        _error: String = "Bad Request",
        _desc: String? = null,
        _data: Any? = null
) : BaseException(HttpStatus.BAD_REQUEST, "BAD_REQUEST", _error, _desc, _data)