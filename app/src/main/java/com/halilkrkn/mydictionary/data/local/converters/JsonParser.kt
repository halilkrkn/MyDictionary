package com.halilkrkn.mydictionary.data.local.converters

import java.lang.reflect.Type

// Buradaki olayın asıl amacı ne?
// Bu interface'i implement eden sınıfların fromJson ve toJson metodlarını override etmesi gerekiyor.
// Bu metodlar sayesinde veritabanına kaydettiğimiz verileri genellikle List tipindeki verileri String'e çeviriyoruz.
// Veritabanından okurken de String'i tekrar bu tiplere çeviriyoruz.
// Bu işlemi yapabilmek için TypeConverter anotasyonunu kullanıyoruz.
// Bu anotasyonu kullanabilmek için de RoomDatabase sınıfımızı @ProvidedTypeConverter anotasyonu ile işaretliyoruz.
interface JsonParser {

    fun <T> fromJson(json: String, type: Type): T?

    fun <T> toJson(obj: T, type: Type): String?
}