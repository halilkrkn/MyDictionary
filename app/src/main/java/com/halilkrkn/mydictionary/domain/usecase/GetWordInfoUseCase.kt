package com.halilkrkn.mydictionary.domain.usecase

import com.halilkrkn.mydictionary.core.util.Resource
import com.halilkrkn.mydictionary.domain.model.WordInfo
import com.halilkrkn.mydictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWordInfoUseCase @Inject constructor(
    private val repository: WordInfoRepository
) {
     operator fun invoke(word: String): Flow<Resource<List<WordInfo>>> {
        if (word.isBlank()){
            return flow {  }
        }
        return repository.getWordInfo(word = word)
    }

}