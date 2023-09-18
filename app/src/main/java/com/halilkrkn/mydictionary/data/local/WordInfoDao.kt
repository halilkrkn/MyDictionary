package com.halilkrkn.mydictionary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.halilkrkn.mydictionary.data.local.entity.WordInfoEntity
import com.halilkrkn.mydictionary.domain.model.WordInfo

@Dao
interface WordInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllWordInfo(infos: List<WordInfoEntity>)

    @Query("SELECT * FROM wordinfoentity WHERE word IN (:words)")
    suspend fun deleteWordInfos(words: List<String>)
    @Query("SELECT * FROM wordinfoentity WHERE word LIKE '%' || :word || '%'")
    suspend fun getWordInfos(word: String): List<WordInfoEntity>
}