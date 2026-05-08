package com.mariii.readdiary.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.mariii.readdiary.data.repository.FakeBookRepository
import com.mariii.readdiary.domain.model.Book

class BookViewModel : ViewModel() {

    private val _books = mutableStateListOf<Book>()

    val books: List<Book> = _books

    init {
        _books.addAll(FakeBookRepository.getBooks())
    }

    fun addBook(book: Book) {
        _books.add(book)
    }

    fun getBookById(id: Int): Book? {
        return _books.find { it.id == id }
    }
}