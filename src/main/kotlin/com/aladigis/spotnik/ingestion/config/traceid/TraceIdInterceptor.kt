package com.aladigis.spotnik.ingestion.config.traceid

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.util.UUID
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class TraceIdInterceptor : HandlerInterceptor {
    companion object {
        const val TRACE_ID_HEADER = "X-Trace-Id"
        const val TRACE_ID_MDC_KEY = "traceId"
    }

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        val traceId = request.getHeader(TRACE_ID_HEADER) ?: UUID.randomUUID().toString()
        MDC.put(TRACE_ID_MDC_KEY, traceId)
        if (request.getHeader(TRACE_ID_HEADER) == null) {
            response.addHeader(TRACE_ID_HEADER, traceId)
        }
        return true
    }
}
