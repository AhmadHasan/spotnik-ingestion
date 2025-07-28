package com.aladigis.spotnik.ingestion.service.component

import com.aladigis.spotnik.ingestion.config.NerConfig
import com.aladigis.spotnik.ingestion.model.LinkedEntity
import com.aladigis.spotnik.ingestion.model.LinkedLabel
import com.aladigis.spotnik.ingestion.port.data.WikidataTypeDataPort
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class LinkedEntityProcessor {
    @Autowired
    private lateinit var nerConfig: NerConfig

    @Autowired
    private lateinit var wikidataTypeDataPort: WikidataTypeDataPort

    fun enrichIfRelevant(linkedEntity: LinkedEntity): LinkedEntity? {
        val rootTypes =
            wikidataTypeDataPort
                .findByIdIn(linkedEntity.instanceOf)
                .map { it.rootTypes }
                .flatten()
                .distinct()

        if (rootTypes.isEmpty()) {
            return null
        } else {
            val spacyTypes = nerConfig.getSpacyTypes(rootTypes).distinct()
            return linkedEntity.copy(
                spacyTypes = spacyTypes,
            )
        }
    }

    fun parseEntity(rawItem: JsonNode): LinkedEntity? {
        // Validate type field
        val type = rawItem.get("type")?.asText()
        if (type == null || type != "item") return null

        // Validate id field
        val id = rawItem.get("id")?.asText() ?: return null

        // Extract descriptions
        val descriptions =
            rawItem
                .get("descriptions")
                ?.properties()
                ?.asSequence()
                ?.associate { (lang, descNode) -> lang to (descNode.get("value")?.asText() ?: "") }
                ?: emptyMap()

        // Extract instanceOf
        val claims = rawItem.get("claims")
        val instanceOf =
            claims
                ?.get("P31")
                ?.elements()
                ?.asSequence()
                ?.mapNotNull { statement ->
                    val mainsnak = statement.get("mainsnak")
                    val datavalue = mainsnak?.get("datavalue")
                    datavalue?.get("value")?.get("id")?.asText()
                }?.toList() ?: emptyList()

        // Extract mainImage
        val mainImage =
            claims
                ?.get("P18")
                ?.elements()
                ?.asSequence()
                ?.firstOrNull { statement ->
                    val mainsnak = statement.get("mainsnak")
                    val datavalue = mainsnak?.get("datavalue")
                    datavalue?.get("type")?.asText() == "string"
                }?.get("mainsnak")
                ?.get("datavalue")
                ?.get("value")
                ?.asText()

        // Extract features
        val features =
            claims
                ?.properties()
                ?.asSequence()
                ?.map { entry ->
                    val property = entry.key
                    val statementsNode = entry.value
                    property to
                        statementsNode
                            .elements()
                            ?.asSequence()
                            ?.mapNotNull { statement ->
                                val mainsnak = statement.get("mainsnak")
                                val datavalue = mainsnak?.get("datavalue")
                                datavalue?.get("value")?.get("id")?.asText()
                            }?.distinct()
                            ?.toList()
                }?.filter { it.second != null }
                ?.associate { it.first to it.second!! }
                ?.filter { it.value.isNotEmpty() } ?: emptyMap()
        // extract wikipediaUrlName from sitelinks
        val sitelinks = rawItem.get("sitelinks")
        // take nodes with keys like "enwiki", "frwiki", etc. and map them to the language codes
        val wikipediaUrlNames =
            sitelinks
                ?.properties()
                ?.asSequence()
                ?.filter { it.key.endsWith("wiki") }
                ?.associate { (key, value) ->
                    val lang = key.substringBefore("wiki")
                    val title = value.get("title")?.asText() ?: ""
                    lang to title
                } ?: emptyMap()

        return LinkedEntity(
            id = id,
            mainImage = mainImage,
            descriptions = descriptions,
            instanceOf = instanceOf,
            features = features.mapValues { it.value },
            spacyTypes = nerConfig.getSpacyTypes(instanceOf),
            wikipediaUrlNames = wikipediaUrlNames,
        )
    }

    fun extractLabels(
        entityId: String,
        rawItem: JsonNode,
    ): List<LinkedLabel> {
        val labels = mutableListOf<LinkedLabel>()

        // Extract main label
        val labelsNode = rawItem.get("labels")
        val mainLabel = labelsNode?.get("en")?.get("value")?.asText()
        if (mainLabel != null) {
            labels.add(LinkedLabel(entityId = entityId, value = mainLabel, language = "en", main = true))
        }

        // Extract other labels
        labelsNode?.properties()?.forEach { (lang, labelData) ->
            val value = labelData.get("value")?.asText()
            if (value != null && lang != "en") {
                labels.add(LinkedLabel(entityId = entityId, value = value, language = lang))
            }
        }

        // Extract aliases
        val aliasesNode = rawItem.get("aliases")
        aliasesNode?.properties()?.forEach { (lang, aliasList) ->
            aliasList.forEach { alias ->
                val value = alias.get("value")?.asText()
                if (value != null) {
                    labels.add(LinkedLabel(entityId = entityId, value = value, language = lang))
                }
            }
        }

        return labels
    }
}
