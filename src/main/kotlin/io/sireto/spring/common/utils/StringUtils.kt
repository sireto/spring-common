package io.sireto.spring.common.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.sireto.spring.common.exceptions.BadRequestException
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

object StringUtils {

    private val UuidRegex = """/^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\$/""".toRegex()
    val objectMapper = jacksonObjectMapper()
    val gson = Gson()

    private fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }

    fun String.toHash(): String {
        val digest: MessageDigest
        try {
            digest = MessageDigest.getInstance("SHA-256")
            val hash = digest.digest(
                this.toByteArray(StandardCharsets.UTF_8)
            )
            return hash.toHex()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }

    fun String.containsOnlyAlphabets() = Regex("[^A-Za-z0-9]").replace(this, "").equals(this)

    fun String.isUuid() =
            try {
                UUID.fromString(this)
                true
            }catch (ex: Exception){
                false
            }

    fun String.toUuid() = UUID.fromString(this)

    fun String.toUuidOrThrow400(): UUID{
        return try {
            UUID.fromString(this)
        }catch (ex: Exception){
            throw BadRequestException("$this is not a valid UUID")
        }
    }

    fun mapToJson(map: Map<String, Any>)= objectMapper.writeValueAsString(map)

    fun jsonToMap(json: String?): Map<String, Any> =
            if (json.isNullOrBlank()) mapOf()
            else gson.fromJson(json, object : TypeToken<Map<String, Any>>() {}.type)

    fun String.toFixedSizeList(size: Int = 64): List<String> {
        val response: MutableList<String> = mutableListOf()
        var splitFrom = 0
        var remainingDataLength = this.length
        while (remainingDataLength > 0){
            val splitLength = if (  remainingDataLength < size ) remainingDataLength else size
            val splitValue = this.substring(splitFrom, splitFrom + splitLength)
            response.add(splitValue)
            splitFrom += splitLength
            remainingDataLength -= splitLength
        }
        return response
    }

    /**
     * Converts the given string to fixed byte size strings.
     * If the string is non-ascii, then char size != byte size.
     */
    fun String.toFixedByteSizeList(size: Int = 64): List<String>{
        val response = mutableListOf<String>()
        var str = this
        while (str.length > size){
            var byteSize = 0
            var index = 0
            for (c in str){
                val charBytes = "$c".toByteArray()
                if((byteSize + charBytes.size) >  size) break else byteSize +=charBytes.size
                index++
                if (byteSize >= size) break
            }
            response.add( str.substring(0, index) )
            str = str.substring(index)
        }
        if (str.isNotEmpty()) response.add(str)
        return response
    }
}