package com.halilkrkn.mydictionary.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.halilkrkn.mydictionary.domain.model.License
import com.halilkrkn.mydictionary.domain.model.Phonetic

data class LicenseDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
) {
    fun toLicence(): License {
        return License(
            name = name,
            url = url
        )
    }
}