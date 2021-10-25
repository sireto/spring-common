package io.sireto.spring.common.exceptions

import org.springframework.http.HttpStatus

class UnprocessableException(
    _error: String = "Unprocessable entity",
    _desc: String? = null,
    _data: Any? = null,
): BaseException(HttpStatus.UNPROCESSABLE_ENTITY, "UNPROCESSABLE_ENTITY", _error, _desc, _data)