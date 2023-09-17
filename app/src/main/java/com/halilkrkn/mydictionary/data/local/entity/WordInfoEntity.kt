package com.halilkrkn.mydictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.halilkrkn.mydictionary.domain.model.Meaning
import com.halilkrkn.mydictionary.domain.model.Phonetic

@Entity
data class WordInfoEntity(
    val meanings: List<Meaning>,
    val phonetic: String,
    val phonetics: List<Phonetic>,
    val word: String,
    @PrimaryKey
    val id: Int? = null
)

