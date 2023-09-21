package com.halilkrkn.mydictionary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.halilkrkn.mydictionary.data.local.converters.Converters
import com.halilkrkn.mydictionary.data.local.entity.WordInfoEntity

@Database(
    entities = [WordInfoEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DictionaryDatabase: RoomDatabase() {

    abstract val dao: DictionaryDao
  
}