package io.sireto.spring.common.exceptions

import org.springframework.http.HttpStatus

class ServiceUnavailableException (_error: String = "Service unavailable",
                                   _desc: String? = null,
                                   _data: Any? = null
) : BaseException(HttpStatus.SERVICE_UNAVAILABLE, "SERVICE_UNAVAILABLE", _error, _desc, _data)