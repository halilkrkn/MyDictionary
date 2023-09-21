package com.halilkrkn.mydictionary.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.halilkrkn.mydictionary.domain.model.Phonetic

data class PhoneticDto(
    @SerializedName("audio")
    val audio: String,
    @SerializedName("license")
    val license: LicenseDto,
    @SerializedName("sourceUrl")
    val sourceUrl: String,
    @SerializedName("text")
    val text: String
) {
    fun toPhonetic(): Phonetic {
        return Phonetic(
            audio = audio,
            sourceUrl = sourceUrl,
            license = license.toLicence(),
            text = text
        )
    }
}