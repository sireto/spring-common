package io.sireto.spring.common.specs

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

enum class SortOrder {
    ASC, DESC
}

class Pagination(
        var pageNumber: Int = 0,
        var pageSize: Int = 25,
        val sortOrder: SortOrder? = null,
        val sortBy: List<String>? = null
) {

    //Prioritize sortBy passed from param instead of sortBy from method varargs
    //If sortBy arguments in pageable is null then sort by passed from request
    fun pageable(vararg sortByArgs: String): Pageable {
        if (pageNumber < 0) pageNumber = 0
        if (pageSize < 0 || pageSize > 100) pageSize = 25
        val sort =
                if (sortBy.isNullOrEmpty()) {
                    //Then sort by args passed in pageable method
                    if (sortByArgs.isEmpty()) null else {
                        when (sortOrder) {
                            null, SortOrder.DESC -> Sort.by(*sortByArgs).descending()
                            SortOrder.ASC -> Sort.by(*sortByArgs).ascending()
                        }
                    }
                } else {
                    when (sortOrder) {
                        null, SortOrder.DESC -> Sort.by(*sortBy.toTypedArray()).descending()
                        SortOrder.ASC -> Sort.by(*sortBy.toTypedArray()).ascending()
                    }
                }

        return if (sort == null) PageRequest.of(pageNumber, pageSize) else PageRequest.of(pageNumber, pageSize, sort)
    }
}