package com.mariii.readdiary.data.source

import com.mariii.readdiary.domain.model.Book
import com.mariii.readdiary.domain.model.ReadingStatus

object BookSearchSource {

    fun getBooks(): List<Book> {

        return listOf(

            Book(
                id = 1,
                title = "В погоне за ускользающим светом",
                author = "О`Келли Юджин",
                category = "Психология",
                rating = 0,
                totalPages = 176,
                currentPage = 0,
                status = ReadingStatus.PLANNED,
                coverUri = "android.resource://com.mariii.readdiary/drawable/book1"
            ),

            Book(
                id = 2,
                title = "Беседы",
                author = "Эпиктет",
                category = "Философия",
                rating = 0,
                totalPages = 480,
                currentPage = 0,
                status = ReadingStatus.PLANNED,
                coverUri = "android.resource://com.mariii.readdiary/drawable/book2"
            ),

            Book(
                id = 3,
                title = "Ночь в Лиссабоне",
                author = "Эрих Мария Ремарк",
                category = "Роман",
                rating = 0,
                totalPages = 300,
                currentPage = 0,
                status = ReadingStatus.PLANNED,
                coverUri = "android.resource://com.mariii.readdiary/drawable/book3"
            )
        )
    }
}