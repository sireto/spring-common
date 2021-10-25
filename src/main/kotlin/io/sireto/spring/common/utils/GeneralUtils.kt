package io.sireto.spring.common.utils

object GeneralUtils {
    fun generateMapWithFixedSizeValueLength(data: String, size: Int = 64): MutableMap<Int, String> {
        val response = mutableMapOf<Int, String>()
        var key = 0
        var splitFrom = 0
        var remainingDataLength = data.length
        while (remainingDataLength > 0){
            val splitLength = if (  remainingDataLength < size ) remainingDataLength else size
            val splitValue = data.substring(splitFrom, splitFrom + splitLength)
            response[key] = splitValue
            key++
            splitFrom += splitLength
            remainingDataLength -= splitLength
        }
        return response
    }
}