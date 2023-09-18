package com.halilkrkn.mydictionary.domain.mappers

import com.halilkrkn.mydictionary.data.local.entity.WordInfoEntity
import com.halilkrkn.mydictionary.data.remote.dto.DefinitionDto
import com.halilkrkn.mydictionary.data.remote.dto.MeaningDto
import com.halilkrkn.mydictionary.data.remote.dto.PhoneticDto
import com.halilkrkn.mydictionary.data.remote.dto.WordInfoDto
import com.halilkrkn.mydictionary.domain.model.Definition
import com.halilkrkn.mydictionary.domain.model.Meaning
import com.halilkrkn.mydictionary.domain.model.Phonetic
import com.halilkrkn.mydictionary.domain.model.WordInfo

fun DefinitionDto.toDefinition(): Definition {
    return Definition(
        definition = definition,
        example = example,
    )
}

fun MeaningDto.toMeaning(): Meaning {
    return Meaning(
        definitions = definitions.map { definitionDto ->
            definitionDto.toDefinition()
        },
        partOfSpeech = partOfSpeech
    )
}

fun PhoneticDto.toPhonetic(): Phonetic {
    return Phonetic(
        audio = audio,
        sourceUrl = sourceUrl,
        text = text
    )
}

fun WordInfoDto.toWordInfoEntity(): WordInfoEntity {
    return WordInfoEntity(
        meanings = meanings.map { meaningDto ->
            meaningDto.toMeaning()
        },
        phonetic = phonetic,
        phonetics = phonetics.map { phoneticDto ->
            phoneticDto.toPhonetic()
        },
        word = word
    )
}

fun WordInfoEntity.toWordInfo(): WordInfo {
    return WordInfo(
        meanings = meanings,
        phonetic = phonetic,
        phonetics = phonetics,
        word = word
    )
}

