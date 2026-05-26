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
    fun addNoteToBook(
        bookId: Int,
        note: ReadingNote
    ) {

        val book = books.value.find {
            it.id == bookId
        } ?: return

        updateBook(

            book.copy(
                notes = book.notes + note
            )
        )
    }
    fun updateNoteInBook(
        bookId: Int,
        updatedNote: ReadingNote
    ) {

        val book = books.value.find {
            it.id == bookId
        } ?: return

        val updatedNotes = book.notes.map { note ->

            if (note.id == updatedNote.id) {
                updatedNote
            } else {
                note
            }
        }

        updateBook(
            book.copy(
                notes = updatedNotes
            )
        )
    }
    fun deleteNoteFromBook(
        bookId: Int,
        noteId: Int
    ) {

        val book = books.value.find {
            it.id == bookId
        } ?: return

        updateBook(
            book.copy(
                notes = book.notes.filter {
                    it.id != noteId
                }
            )
        )
    }

}