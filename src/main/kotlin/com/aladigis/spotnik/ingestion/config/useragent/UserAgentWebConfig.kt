package com.aladigis.spotnik.ingestion.config.useragent

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class UserAgentWebConfig : WebMvcConfigurer {
    @Autowired
    private lateinit var userAgentInterceptor: UserAgentInterceptor

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(userAgentInterceptor)
    }
}
