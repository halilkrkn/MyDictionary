package com.halilkrkn.mydictionary.domain.repository

import com.halilkrkn.mydictionary.core.util.Resource
import com.halilkrkn.mydictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {
    suspend fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>
}