package com.mariii.readdiary.data.local.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {

        return INSTANCE ?: synchronized(this) {

            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "read_diary_database"
            )
                .fallbackToDestructiveMigration() // временно, пока структура проекта меняется. удаляет и создает заново БД без краша приложения
                .build()

            INSTANCE = instance

            instance
        }
    }
}