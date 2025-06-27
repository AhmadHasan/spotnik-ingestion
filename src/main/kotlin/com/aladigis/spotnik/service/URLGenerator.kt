package com.aladigis.spotnik.service

import org.springframework.stereotype.Component
import java.security.MessageDigest

@Component
class URLGenerator {
    companion object {
        private const val WIKIPEDIA_URL_TEMPLATE = "https://{language}.wikipedia.org/wiki/{UrlEncodedLabel}"
        private const val MAIN_IMAGE_URL_TEMPLATE = "https://upload.wikimedia.org/wikipedia/commons/{1char}/{2chars}/{image_name_encoded}"
    }

    fun generateEntityWikipediaUrl(
        language: String,
        label: String,
    ): String {
        val labelUrlEncoded = label.replace(" ", "_")
        return WIKIPEDIA_URL_TEMPLATE
            .replace("{language}", language)
            .replace("{UrlEncodedLabel}", labelUrlEncoded)
    }

    fun generateEntityImageUrl(rawImageName: String): String {
        if (rawImageName.isBlank() || listOf("null", "undefined", "none.png").contains(rawImageName)) {
            return ""
        }

        val imageNameEncoded = rawImageName.replace(" ", "_")
        val md5Hash = MessageDigest.getInstance("MD5").digest(imageNameEncoded.toByteArray(Charsets.UTF_8))
        val md5Hex = md5Hash.joinToString("") { "%02x".format(it) }

        val char1 = md5Hex.substring(0, 1)
        val chars2 = md5Hex.substring(0, 2)

        return "https://upload.wikimedia.org/wikipedia/commons/{1char}/{2chars}/{image_name_encoded}"
            .replace("{1char}", char1)
            .replace("{2chars}", chars2)
            .replace("{image_name_encoded}", imageNameEncoded)
    }
}
