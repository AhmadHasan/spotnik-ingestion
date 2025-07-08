package com.aladigis.spotnik.ingestion.config.traceid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class TraceIdWebConfig : WebMvcConfigurer {
    @Autowired
    private lateinit var traceIdInterceptor: TraceIdInterceptor

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(traceIdInterceptor)
    }
}
