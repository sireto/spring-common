package io.sireto.spring.common.exceptions

import org.springframework.http.HttpStatus

class ServerException (
        _error: String = "Something went wrong on our end",
        _desc: String? = null,
        _data: Any? = null) : BaseException(HttpStatus.EXPECTATION_FAILED, "SOMETHING_WENT_WRONG", _error, _desc, _data)