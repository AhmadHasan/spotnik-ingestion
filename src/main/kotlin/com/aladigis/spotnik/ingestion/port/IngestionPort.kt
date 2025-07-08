package com.aladigis.spotnik.ingestion.port

interface IngestionPort {
    fun ingest(fileName: String, fromLine: Int = 0, toLine: Int = Int.MAX_VALUE)
}