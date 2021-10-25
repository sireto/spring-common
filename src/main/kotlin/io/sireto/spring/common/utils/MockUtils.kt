package io.sireto.spring.common.utils

import io.sireto.spring.common.components.JwtProperties
import io.sireto.spring.common.exceptions.BaseException
import io.sireto.spring.common.utils.ResponseParserUtils.toJson
import org.junit.jupiter.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import javax.annotation.PostConstruct

class MockUtils @Autowired constructor(
    private val mockMvc: MockMvc,
    private val jwtProperties: JwtProperties
){

    // Useful utilities and fields
     var basicHeaders = HttpHeaders()

    @PostConstruct
    fun postConstruct() {
        basicHeaders.contentType = MediaType.APPLICATION_JSON
    }

     fun getReq(path: String, authHeader: String? = null,
                         params: Map<String, String> = mapOf(), headers: Map<String, String>? = null): MockHttpServletRequestBuilder {
        addHeaders(headers)
        val requestBuilder = MockMvcRequestBuilders.get(path).headers(basicHeaders)
        params.forEach { requestBuilder.param(it.key, it.value) }
        return if (authHeader.isNullOrBlank()) requestBuilder else requestBuilder.header("Authorization", authHeader)
    }

     fun postReq(path: String, requestBody: Any, authHeader: String? = null,
                          params: Map<String, String>? = mapOf(), headers: Map<String, String>? = null): MockHttpServletRequestBuilder {
        addHeaders(headers)
        val requestBuilder = MockMvcRequestBuilders.post(path).content(toJson(requestBody)).headers(basicHeaders)
        return prepareRequest(requestBuilder, params, authHeader)
    }

     fun patchReq(path: String, requestBody: Any, authHeader: String? = null,
                           params: Map<String, String>? = mapOf(), headers: Map<String, String>? = null): MockHttpServletRequestBuilder {
        addHeaders(headers)
        val requestBuilder = MockMvcRequestBuilders.patch(path).content(toJson(requestBody)).headers(basicHeaders)
        return prepareRequest(requestBuilder, params, authHeader)
    }

     fun putReq(path: String, requestBody: Any, authHeader: String? = null,
                         params: Map<String, String>? = mapOf(), headers: Map<String, String>? = null): MockHttpServletRequestBuilder {
        addHeaders(headers)
        val requestBuilder = MockMvcRequestBuilders.put(path).content(toJson(requestBody)).headers(basicHeaders)
        return prepareRequest(requestBuilder, params, authHeader)
    }

     fun delReq(path: String, authHeader: String? = null,
                         params: Map<String, String>? = mapOf(), headers: Map<String, String>? = null): MockHttpServletRequestBuilder {
        addHeaders(headers)
        val requestBuilder = MockMvcRequestBuilders.delete(path).headers(basicHeaders)
        return prepareRequest(requestBuilder, params, authHeader)
    }

    /*********************** request preparation ***************************/
    private fun prepareRequest(builder: MockHttpServletRequestBuilder, params: Map<String, String>? = mapOf(), authHeader: String?): MockHttpServletRequestBuilder {
        params?.forEach { builder.param(it.key, it.value) }
        return if (authHeader.isNullOrBlank()) builder else builder.header("Authorization", authHeader)
    }

    /*********************** perform requests ***************************/

     fun performAndExpectOk(builder: MockHttpServletRequestBuilder): String{
        val mvcResult = mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.OK.value())).andReturn()
        return ResponseParserUtils.parsePayloadString(mvcResult)
    }

     fun <T> performAndExpectOk(builder: MockHttpServletRequestBuilder, result: Class<T>): T{
        val mvcResult = mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.OK.value())).andReturn()
        return ResponseParserUtils.parse(mvcResult, result)
    }

     fun performAndExpectException(request: MockHttpServletRequestBuilder, ex: BaseException): Exception? {
        val mvcResult = mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().`is`(ex.httpStatus.value())).andReturn()
        val resolvedException = mvcResult.resolvedException
        Assertions.assertTrue(resolvedException?.javaClass == ex::class.java || resolvedException?.cause?.javaClass == ex::class.java)
        return resolvedException
    }

    /*********************** internal helpers ***************************/

    fun addHeaders(headers: Map<String, String>?){
        headers?.forEach { basicHeaders.set(it.key, it.value) }
        basicHeaders.contentType = MediaType.APPLICATION_JSON
    }

    /*********************** auth headers ***************************/
    fun getAuthHeader(user: String) = mapOf(Pair(jwtProperties.headerKey, jwtProperties.tokenPrefix+" $user"))
}