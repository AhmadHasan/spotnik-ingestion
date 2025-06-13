package com.aladigis.spotnik.config

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.util.ContentCachingResponseWrapper
import java.nio.charset.Charset

@Component
class RequestResponseLoggingFilter : Filter {
    companion object {
        private const val MAX_RESPONSE_SIZE = 128
    }

    private val logger = LoggerFactory.getLogger(RequestResponseLoggingFilter::class.toString())

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain,
    ) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse
        val wrappingResponse = ContentCachingResponseWrapper(httpResponse)
        val startTime = System.currentTimeMillis()

        try {
            chain.doFilter(request, wrappingResponse)
        } finally {
            val responseTime = System.currentTimeMillis() - startTime
            logRequestAndResponse(httpRequest, wrappingResponse, responseTime)

            wrappingResponse.copyBodyToResponse()
        }
    }

    private fun logRequestAndResponse(
        request: HttpServletRequest,
        response: ContentCachingResponseWrapper,
        responseTime: Long,
    ) {
        val responseLog = getResponseLog(response)
        logger.info(
            "Request URI: {}, Method: {} Response Status: {}, Response Time: {} ms, response(1024): {}",
            request.requestURI,
            request.method,
            response.status,
            responseTime,
            responseLog,
        )
    }

    private fun getResponseLog(response: ContentCachingResponseWrapper): String {
        val responseString =
            String(response.contentAsByteArray, Charset.forName("UTF-8"))
                .replace("\n", " ") // replace newlines with spaces
                .replace("\r", "") // remove carriage returns
                .trim() // remove leading/trailing whitespace

        return if (responseString.length > MAX_RESPONSE_SIZE) {
            "${responseString.substring(0, MAX_RESPONSE_SIZE)}[$MAX_RESPONSE_SIZE of ${responseString.length} chars]"
        } else {
            responseString
        }
    }
}
