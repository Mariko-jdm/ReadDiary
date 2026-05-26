package com.mariii.readdiary.data.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mariii.readdiary.domain.model.ReadingNote
import com.mariii.readdiary.domain.model.ReadingStatus

class Converters {

    @TypeConverter
    fun fromReadingStatus(status: ReadingStatus): String {
        return status.name
    }

    @TypeConverter
    fun toReadingStatus(value: String): ReadingStatus {
        return ReadingStatus.valueOf(value)
    }

    @TypeConverter
    fun fromNotes(notes: List<ReadingNote>): String {
        return Gson().toJson(notes)
    }

    @TypeConverter
    fun toNotes(data: String): List<ReadingNote> {

        val type = object : TypeToken<List<ReadingNote>>() {}.type

        return Gson().fromJson(data, type)
    }
}