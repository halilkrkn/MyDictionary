package com.halilkrkn.mydictionary.domain.model

import com.halilkrkn.mydictionary.data.remote.dto.LicenseDto

data class Phonetic(
    val audio: String,
    val sourceUrl: String,
    val license: License,
    val text: String
)