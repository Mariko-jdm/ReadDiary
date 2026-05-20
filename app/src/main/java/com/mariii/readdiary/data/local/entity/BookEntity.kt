package com.mariii.readdiary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mariii.readdiary.domain.model.ReadingStatus

@Entity(tableName = "books")

data class BookEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val author: String,

    val category: String,
    val rating: Int,

    val totalPages: Int,
    val currentPage: Int,

    val status: ReadingStatus,

    val coverUri: String = ""
)