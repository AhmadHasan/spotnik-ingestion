package com.aladigis.spotnik.service

import org.springframework.stereotype.Component

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

    fun generateEntityImageUrl(imageName: String): String  {
        if(imageName.isBlank() || imageName == "null" || imageName == "undefined" || imageName == "none" || imageName == "null.png" || imageName == "undefined.png" || imageName == "none.png") {
            return ""
        }
        val imageNameEncoded = java.net.URLEncoder.encode(imageName, "UTF-8")
            .replace("+", "_")
        val imageNameHash =
            java.util.Base64.getEncoder().encodeToString(imageNameEncoded.toByteArray())

        val char1 = imageNameHash.substring(0, 1)
        val chars2 = imageNameHash.substring(0, 2)
        return MAIN_IMAGE_URL_TEMPLATE
            .replace("{1char}", char1)
            .replace("{2chars}", chars2)
            .replace("{image_name_encoded}", imageNameEncoded)
    }
}
