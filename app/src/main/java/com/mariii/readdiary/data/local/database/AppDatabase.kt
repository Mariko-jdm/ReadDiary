package com.mariii.readdiary.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mariii.readdiary.data.local.dao.BookDao
import com.mariii.readdiary.data.local.entity.BookEntity

@Database(
    entities = [
        BookEntity::class
    ],
    version = 3,
    exportSchema = false
)

@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao
}