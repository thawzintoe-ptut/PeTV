package com.ptut.pmovie.core.database.util.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreIdsConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromList(list: List<Int>?): String? {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toArrayList(data: String?): List<Int>? {
        if (data == null) {
            return null
        }
        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(data, listType)
    }
}
