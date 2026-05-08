package com.mariii.readdiary.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.mariii.readdiary.domain.model.Book
import com.mariii.readdiary.domain.model.ReadingStatus

object FakeBookRepository {

    private val books = mutableStateListOf(
        Book(
            id = 1,
            title = "Вино из одуванчиков",
            author = "Рэй Брэдбери",
            totalPages = 300,
            currentPage = 120,
            status = ReadingStatus.READING
        ),

        Book(
            id = 2,
            title = "Маленький принц",
            author = "Антуан де Сент-Экзюпери",
            totalPages = 150,
            currentPage = 150,
            status = ReadingStatus.FINISHED
        )
    )

    fun getBooks(): List<Book> {
        return books
    }

    fun addBook(book: Book) {
        books.add(book)
    }

    fun getBookById(id: Int): Book? {
        return books.find { it.id == id }
    }

    fun updateBook(updatedBook: Book) {

        val index = books.indexOfFirst {
            it.id == updatedBook.id
        }

        if (index != -1) {
            books[index] = updatedBook
        }
    }
}