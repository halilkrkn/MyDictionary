package com.halilkrkn.mydictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.halilkrkn.mydictionary.domain.model.Meaning
import com.halilkrkn.mydictionary.domain.model.Phonetic
import com.halilkrkn.mydictionary.domain.model.WordInfo

@Entity(tableName = "wordInfo")
data class WordInfoEntity(
    val meanings: List<Meaning>,
//    val phonetic: String,
//    val phonetics: List<Phonetic>?,
    val word: String,
    @PrimaryKey
    val id: Int? = null
){
    fun toWordInfo(): WordInfo {
    return WordInfo(
        meanings = meanings,
        word = word,
//        phonetic = phonetic,
//        phonetics = phonetics
    )
}

}
//
//data class MeaningsEntity(
//    val meaning: List<Meaning>
//)
//
//data class PhoneticEntity(
//    val phonetic: List<Phonetic>
//)

