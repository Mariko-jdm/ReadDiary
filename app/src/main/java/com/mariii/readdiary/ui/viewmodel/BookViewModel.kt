package com.mariii.readdiary.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariii.readdiary.data.repository.BookRepository
import com.mariii.readdiary.domain.model.Book
import com.mariii.readdiary.domain.model.ReadingNote
import com.mariii.readdiary.domain.model.ReadingStatus
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookViewModel(
    private val repository: BookRepository
) : ViewModel() {

    val books: StateFlow<List<Book>> =
        repository.getBooks()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

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

        val currentBooks = books.value

        val updatedBooks = currentBooks.map { book ->

            if (book.id == bookId) {

                book.copy(
                    notes = book.notes + note
                )

            } else {
                book
            }
        }

        updatedBooks.forEach { updatedBook ->
            updateBook(updatedBook)
        }
    }

    fun updateReadingProgress(
        book: Book,
        newPage: Int,
        newStatus: ReadingStatus
    ) {

        viewModelScope.launch {

            repository.updateBook(
                book.copy(
                    currentPage = newPage,
                    status = newStatus
                )
            )
        }
    }
}