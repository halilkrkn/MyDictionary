package com.halilkrkn.mydictionary.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.halilkrkn.mydictionary.core.Const.BASE_URL
import com.halilkrkn.mydictionary.data.local.DictionaryDao
import com.halilkrkn.mydictionary.data.local.DictionaryDatabase
import com.halilkrkn.mydictionary.data.local.converters.Converters
import com.halilkrkn.mydictionary.data.local.converters.GsonParser
import com.halilkrkn.mydictionary.data.remote.DictionaryApi
import com.halilkrkn.mydictionary.data.repository.WordInfoRepositoryImpl
import com.halilkrkn.mydictionary.domain.repository.WordInfoRepository
import com.halilkrkn.mydictionary.domain.usecase.GetWordInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGetWordInfoUseCase(repository: WordInfoRepository): GetWordInfoUseCase {
        return GetWordInfoUseCase(repository = repository)
    }


    @Provides
    @Singleton
    fun provideWordInfoRepository(
        api: DictionaryApi,
        db: DictionaryDatabase
    ): WordInfoRepository {
        return WordInfoRepositoryImpl(api,db.dao)
    }

    @Provides
    @Singleton
    fun provideDictionaryDatabase(app: Application): DictionaryDatabase {
        return Room.databaseBuilder(
            context = app,
            klass = DictionaryDatabase::class.java,
            name = "dictionary_db"
        )
            .addTypeConverter(Converters(GsonParser(Gson())))
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }

}