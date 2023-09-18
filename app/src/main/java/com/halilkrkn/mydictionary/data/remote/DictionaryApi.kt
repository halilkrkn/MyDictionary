package com.halilkrkn.mydictionary.data.remote

import com.halilkrkn.mydictionary.data.remote.dto.WordInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    @GET("https://api.dictionaryapi.dev/api/v2/entries/en/{word}")
    suspend fun getWordInfo(
        @Path("word") word: String
    ): List<WordInfoDto>

}