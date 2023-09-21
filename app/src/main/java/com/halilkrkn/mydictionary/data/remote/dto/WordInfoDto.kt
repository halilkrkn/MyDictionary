package com.halilkrkn.mydictionary.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.halilkrkn.mydictionary.data.local.entity.WordInfoEntity
//import com.halilkrkn.mydictionary.domain.mappers.toPhonetic

data class WordInfoDto(
    @SerializedName("license")
    val license: LicenseDto,
    @SerializedName("meanings")
    val meanings: List<MeaningDto>,
//    @SerializedName("phonetic")
//    val phonetic: String,
//    @SerializedName("phonetics")
//    val phonetics: List<PhoneticDto>?,
    @SerializedName("sourceUrls")
    val sourceUrls: List<String>,
    @SerializedName("word")
    val word: String
) {
    fun toWordInfoEntity(): WordInfoEntity {
        return WordInfoEntity(
            meanings = meanings.map { it.toMeaning() },
//            phonetic = phonetic,
//            phonetics = phonetics?.map { it.toPhonetic()},
            word = word
        )
    }
}