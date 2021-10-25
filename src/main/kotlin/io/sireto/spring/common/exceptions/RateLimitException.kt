package io.sireto.spring.common.exceptions

import org.springframework.http.HttpStatus

class RateLimitException(
    _error: String = "Rate Limit",
    _desc: String? = null,
    _data: Any? = null,
): BaseException(HttpStatus.TOO_MANY_REQUESTS, "TOO_MANY_REQUESTS", _error, _desc, _data)
