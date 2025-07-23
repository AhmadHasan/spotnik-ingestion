package com.aladigis.spotnik.ingestion.adapter.repository

import com.aladigis.spotnik.ingestion.config.traceid.TraceIdInterceptor
import com.aladigis.spotnik.ingestion.model.LinkedEntity
import com.aladigis.spotnik.ingestion.port.data.LinkedEntityDataPort
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class LinkedEntityDataService : LinkedEntityDataPort {
    @Autowired
    private lateinit var linkedEntityRepository: LinkedEntityRepository

    val logger = LoggerFactory.getLogger(LinkedEntityDataService::class.java)

    override fun findByIds(ids: List<String>): List<LinkedEntity> {
        val startTime = System.currentTimeMillis()
        val traceId = MDC.get(TraceIdInterceptor.TRACE_ID_MDC_KEY) ?: "unknown"

        val result = linkedEntityRepository.findAllById(ids)

        val endTime = System.currentTimeMillis()
        //TODO: Set a flag int the config to log all times
        // logger.info("Trace ID: $traceId - Repository call findByIds took ${endTime - startTime} ms")
        return result
    }

    override fun saveAll(entities: List<LinkedEntity>): List<LinkedEntity> {
        val startTime = System.currentTimeMillis()
        val result = linkedEntityRepository.saveAll(entities)
       // logger.info("Repository call saveAll with ${entities.size} entities took ${System.currentTimeMillis() - startTime} ms")
        return result
    }
}
