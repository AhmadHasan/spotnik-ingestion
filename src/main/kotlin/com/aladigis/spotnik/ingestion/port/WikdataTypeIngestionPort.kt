package com.aladigis.spotnik.ingestion.port

interface WikdataTypeIngestionPort {
    fun ingestSubtypes(fileName: String)
}
