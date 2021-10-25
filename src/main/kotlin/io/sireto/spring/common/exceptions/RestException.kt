package io.sireto.spring.common.exceptions

import org.springframework.http.HttpStatus

class RestException(
    _status: HttpStatus,
    _error: String,
    _desc: String? = null,
    _data: Any? = null
): BaseException(_status, _status.name, _error, _desc, _data)
