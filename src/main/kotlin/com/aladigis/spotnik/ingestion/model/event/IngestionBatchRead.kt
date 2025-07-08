package com.aladigis.spotnik.ingestion.model.event

import org.springframework.context.ApplicationEvent

data class IngestionBatchRead (val lines: List<String>):
    ApplicationEvent(lines)