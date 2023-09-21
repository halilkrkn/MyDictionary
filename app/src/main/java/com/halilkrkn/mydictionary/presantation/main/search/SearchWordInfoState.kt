package com.halilkrkn.mydictionary.presantation.main.search

import com.halilkrkn.mydictionary.domain.model.WordInfo

data class SearchWordInfoState(
    val wordInfoItems: List<WordInfo> = emptyList(),
    val isLoading: Boolean = false
)
