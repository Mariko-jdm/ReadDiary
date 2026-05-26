package com.mariii.readdiary.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mariii.readdiary.data.local.dao.BookDao
import com.mariii.readdiary.data.local.dao.ReadingNoteDao
import com.mariii.readdiary.data.local.entity.BookEntity
import com.mariii.readdiary.data.local.entity.ReadingNoteEntity

@Database(
    entities = [
        BookEntity::class,
        ReadingNoteEntity::class
    ],
    version = 2
)

@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    abstract fun readingNoteDao(): ReadingNoteDao
}