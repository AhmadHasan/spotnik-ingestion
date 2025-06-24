package com.aladigis.spotnik.service

import java.net.URLEncoder
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
    ): String  {
        val labelUrlEncoded = java.net.URLEncoder.encode(label, "UTF-8")
        return WIKIPEDIA_URL_TEMPLATE
            .replace("{language}", language)
            .replace("{UrlEncodedLabel}", labelUrlEncoded)
    }


    fun generateEntityImageUrl(rawImageName: String): String {
        // Handle empty or invalid image names
        if (rawImageName.isBlank() || rawImageName == "null" || rawImageName == "undefined" || rawImageName == "none" || rawImageName == "null.png" || rawImageName == "undefined.png" || rawImageName == "none.png") {
            return ""
        }

        // 1. Calculate MD5 hash from the RAW image name (as Wikimedia does)
        val imageNameEncoded = rawImageName.replace(" ", "_")
        val md5Hash = MessageDigest.getInstance("MD5").digest(imageNameEncoded.toByteArray(Charsets.UTF_8))
        val md5Hex = md5Hash.joinToString("") { "%02x".format(it) }

        val char1 = md5Hex.substring(0, 1)
        val chars2 = md5Hex.substring(0, 2)

        // 2. URL-encode the RAW image name for the final path segment
        // Wikimedia Commons specifically replaces '+' with '_' for spaces in the encoded part


        // Assuming MAIN_IMAGE_URL_TEMPLATE is defined elsewhere, e.g.,
        // const val MAIN_IMAGE_URL_TEMPLATE = "https://upload.wikimedia.org/wikipedia/commons/{1char}/{2chars}/{image_name_encoded}"
        return "https://upload.wikimedia.org/wikipedia/commons/{1char}/{2chars}/{image_name_encoded}"
            .replace("{1char}", char1)
            .replace("{2chars}", chars2)
            .replace("{image_name_encoded}", imageNameEncoded)
    }
}
