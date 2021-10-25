package io.sireto.spring.common.utils

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.google.gson.Gson
import io.sireto.spring.common.utils.StringUtils.objectMapper
import org.json.JSONObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.web.servlet.MvcResult
import java.util.stream.Collectors

object ResponseParserUtils {
    val gson = Gson()

    // *************************** Helper functions *************************** //

    fun toJson(`object`: Any): String {
        return try {
            objectMapper.writeValueAsString(`object`)
        } catch (e: JsonProcessingException) {
            throw RuntimeException(e)
        }
    }

    fun parsePayloadString(result: MvcResult) = JSONObject(result.response.contentAsString).get("payload").toString()

    fun <T> parse(result: MvcResult, contentClass: Class<T>): T {
        if(contentClass.typeName == Page::class.java.typeName)
            return parsePage(result.response.contentAsString, contentClass)
        return parse(result.response.contentAsString, contentClass)
    }

    fun <T> parse(response: String, contentClass: Class<T>): T {
        val payload = JSONObject(response).get("payload")
        return objectMapper.readValue(payload.toString(), contentClass)
    }

    fun <T> parsePage(response: String, contentClass: Class<T>):  T {
        val responseMap = objectMapper.readValue(response, object : TypeReference<Map<String, *>>() {})
        val responseJson = gson.toJsonTree(responseMap).asJsonObject
        val payload = responseJson.get("payload").asJsonObject
        val content = payload.get("content").asJsonArray
        val readValue = objectMapper.readValue(content.toString(), object : TypeReference<List<Map<String, *>>>() {})
        /*
         using kMapper instead of gson for mapping because we have the DTO fields annotated with @JsonProperty
         which gson would not correctly map
         */
        val objects = readValue.stream().map {
            objectMapper.readValue(gson.toJson(it), contentClass)
        }.collect(Collectors.toList())
        return PageImpl(
            objects,
            PageRequest.of(payload.get("number").asInt, payload.get("size").asInt),
            payload.get("totalElements").asLong
        ) as T
    }
}