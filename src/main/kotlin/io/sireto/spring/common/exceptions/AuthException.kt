package io.sireto.spring.common.exceptions

import org.springframework.http.HttpStatus

class AuthException( _error: String = "Authentication failure",
                          _desc: String? = null,
                          _data: Any? = null
) : BaseException(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", _error, _desc, _data)
