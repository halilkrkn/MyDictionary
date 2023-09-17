package com.halilkrkn.mydictionary.domain.model

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String,
)