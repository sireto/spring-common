package io.sireto.spring.common.exceptions

import org.springframework.http.HttpStatus

class DuplicateException(
        _error: String = "Duplication of resource or request occured.",
        _desc: String? = null,
        _data: Any? = null
) : BaseException(HttpStatus.CONFLICT, "DUPLICATE_EXCEPTION", _error, _desc, _data)