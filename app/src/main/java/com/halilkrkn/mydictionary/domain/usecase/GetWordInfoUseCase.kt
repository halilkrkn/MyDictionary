package com.halilkrkn.mydictionary.domain.usecase

import com.halilkrkn.mydictionary.core.util.Resource
import com.halilkrkn.mydictionary.domain.model.WordInfo
import com.halilkrkn.mydictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWordInfoUseCase(
    private val repository: WordInfoRepository
) {
    suspend operator fun invoke(word: String): Flow<Resource<List<WordInfo>>> {
        if (word.isBlank()){
            return flow {  }
        }
        return repository.getWordInfo(word = word)
    }

}