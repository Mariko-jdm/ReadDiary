package com.mariii.readdiary.data.local.database

import androidx.room.TypeConverter
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
}