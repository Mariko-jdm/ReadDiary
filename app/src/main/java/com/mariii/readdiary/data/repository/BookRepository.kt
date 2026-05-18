package com.mariii.readdiary.data.repository

import com.mariii.readdiary.data.local.dao.BookDao
import com.mariii.readdiary.data.mapper.toDomain
import com.mariii.readdiary.data.mapper.toEntity
import com.mariii.readdiary.domain.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepository(
    private val bookDao: BookDao
) {

    fun getBooks(): Flow<List<Book>> {

        return bookDao.getBooks().map { list ->
            list.map { it.toDomain() }
        }
    }
    suspend fun insertBook(book: Book) {
        bookDao.insertBook(book.toEntity())
    }

    suspend fun updateBook(book: Book) {
        bookDao.updateBook(book.toEntity())
    }

    suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book.toEntity())
    }
}