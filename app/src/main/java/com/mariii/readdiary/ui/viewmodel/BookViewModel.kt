package com.mariii.readdiary.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariii.readdiary.data.repository.BookRepository
import com.mariii.readdiary.domain.model.Book
import com.mariii.readdiary.domain.model.ReadingNote
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookViewModel(
    private val repository: BookRepository
) : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())

    val books: StateFlow<List<Book>> = _books.asStateFlow()

    init {
        loadBooks()
    }

    private fun loadBooks() {

        viewModelScope.launch {

            repository.getBooks().collect { booksList ->
                _books.value = booksList
            }
        }
    }

    fun addBook(book: Book) {

        viewModelScope.launch {
            repository.insertBook(book)
        }
    }

    fun updateBook(book: Book) {

        viewModelScope.launch {
            repository.updateBook(book)
        }
    }

    fun deleteBook(book: Book) {

        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }
    fun addNoteToBook(
        bookId: Int,
        note: ReadingNote
    ) {

        val updatedBooks = _books.value.map { book ->

            if (book.id == bookId) {

                book.copy(
                    notes = book.notes + note
                )

            } else {
                book
            }
        }

        _books.value = updatedBooks
    }
}