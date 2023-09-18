package com.halilkrkn.mydictionary.domain.usecase

import com.halilkrkn.mydictionary.core.util.Resource
import com.halilkrkn.mydictionary.data.local.WordInfoDao
import com.halilkrkn.mydictionary.domain.mappers.toWordInfo
import com.halilkrkn.mydictionary.domain.mappers.toWordInfoEntity
import com.halilkrkn.mydictionary.domain.model.WordInfo
import com.halilkrkn.mydictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetWordInfoUseCase(
    private val repository: WordInfoRepository,
    private val dao: WordInfoDao
) {
    suspend operator fun invoke(word: String): Flow<Resource<List<WordInfo>>>  = flow {
        emit(Resource.Loading())
        //RoomDatabase için
        val wordInfos = dao.getWordInfos(word = word).map { wordInfoEntity ->
            wordInfoEntity.toWordInfo()
        }
        emit(Resource.Loading(data = wordInfos))

        // Api için
        try {
            val remoteWordInfo = repository.getWordInfo(word)
            dao.deleteWordInfos(words = remoteWordInfo.map { wordInfoDto ->
                wordInfoDto.word
            })
            dao.insertAllWordInfo(infos = remoteWordInfo.map { wordInfoDto ->
                wordInfoDto.toWordInfoEntity()
            })
        }catch (e: HttpException) {
            emit(Resource.Error(
                message = e.localizedMessage ?: "An unexpected error occurred",
                data = wordInfos
            ))
        }
        catch (e: IOException) {
            emit(Resource.Error(
                message = e.localizedMessage ?: "Couldn't reach server. Check your internet connection",
                data = wordInfos
            ))
        }
        val newWordInfos = dao.getWordInfos(word = word).map { wordInfoEntity ->
            wordInfoEntity.toWordInfo()
        }
        emit(Resource.Success(data = newWordInfos))
    }
}