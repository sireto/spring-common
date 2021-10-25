package io.sireto.spring.common.exceptions

import org.springframework.http.HttpStatus

class ForbiddenException ( _error: String = "Forbidden",
                           _desc: String? = null,
                           _data: Any? = null
) : BaseException(HttpStatus.FORBIDDEN, "FORBIDDEN", _error, _desc, _data)