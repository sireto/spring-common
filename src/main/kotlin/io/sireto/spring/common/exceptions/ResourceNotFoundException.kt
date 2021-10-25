package io.sireto.spring.common.exceptions

import org.springframework.http.HttpStatus

class ResourceNotFoundException(
        _error: String = "The requested resource could not be found.",
        _desc: String? = null,
        _data: Any? = null) : BaseException(HttpStatus.NOT_FOUND, "RESOURCE NOT FOUND", _error, _desc, _data)