package com.halilkrkn.mydictionary.data.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.halilkrkn.mydictionary.domain.model.Meaning
import com.halilkrkn.mydictionary.domain.model.Phonetic
import java.util.ArrayList

// Bu Converters işlemini WordInfoEntity sınıfı için yapmamız gerekiyor.
// Çünkü içerisinde List<Meaning> ve List<Phonetic> tipleri var. Yani kısaca List tiplerini RoomDatabase tanımıyor ve veritabanına kaydedemiyor.
// Bu yüzden bu tipleri String'e çevirip veritabanına kaydediyoruz. Veritabanından okurken de String'i tekrar bu tiplere çeviriyoruz.
// Bu işlemi yapabilmek için TypeConverter anotasyonunu kullanıyoruz.
// Bu anotasyonu kullanabilmek için de RoomDatabase sınıfımızı @ProvidedTypeConverter anotasyonu ile işaretliyoruz.
@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser,
//    private val gson: Gson,
) {
/*    @TypeConverter
    fun fromMeaningsJson(json: String): MeaningsEntity {
        return gson.fromJson(json, MeaningsEntity::class.java)
    }

    @TypeConverter
    fun toMeaningsJson(meanings: MeaningsEntity): String {
        return gson.toJson(meanings)
    }

    @TypeConverter
    fun fromPhoneticsJson(json: String): PhoneticEntity {
        return gson.fromJson(json, PhoneticEntity::class.java)
    }

    @TypeConverter
    fun toPhoneticsJson(phonetics: PhoneticEntity): String {
        return gson.toJson(phonetics)
        */

    @TypeConverter
    fun fromMeaningsJson(json: String): List<Meaning> {
        return jsonParser.fromJson<ArrayList<Meaning>>(
            json,
            object : TypeToken<ArrayList<Meaning>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toMeaningsJson(meanings: List<Meaning>): String {
        return jsonParser.toJson(
            meanings,
            object : TypeToken<ArrayList<Meaning>>(){}.type
        ) ?: "[]"
    }
    @TypeConverter
    fun fromPhoneticJson(json: String): List<Phonetic> {
        return jsonParser.fromJson<ArrayList<Phonetic>>(
            json,
            object : TypeToken<ArrayList<Phonetic>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toPhoneticJson(phonetic: List<Phonetic>): String {
        return jsonParser.toJson(
            phonetic,
            object : TypeToken<ArrayList<Phonetic>>(){}.type
        ) ?: "[]"
    }
    }
