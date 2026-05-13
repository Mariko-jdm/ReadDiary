package com.mariii.readdiary.data.mapper

import com.mariii.readdiary.data.local.entity.BookEntity
import com.mariii.readdiary.domain.model.Book

fun BookEntity.toDomain(): Book {

    return Book(
        id = id,
        title = title,
        author = author,
        category = category,
        rating = rating,
        totalPages = totalPages,
        currentPage = currentPage,
        status = status,
        notes = emptyList()
    )
}

fun Book.toEntity(): BookEntity {

    return BookEntity(
        id = id,
        title = title,
        author = author,
        category = category,
        rating = rating,
        totalPages = totalPages,
        currentPage = currentPage,
        status = status
    )
}