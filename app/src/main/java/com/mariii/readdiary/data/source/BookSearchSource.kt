package com.mariii.readdiary.data.source

import com.mariii.readdiary.domain.model.Book
import com.mariii.readdiary.domain.model.ReadingStatus

object BookSearchSource {

    fun getBooks(): List<Book> {

        return listOf(

            Book(
                id = 1,
                title = "1984",
                author = "Джордж Оруэлл",
                category = "Антиутопия",
                rating = 0,
                totalPages = 328,
                currentPage = 0,
                status = ReadingStatus.PLANNED
            ),

            Book(
                id = 2,
                title = "Мартин Иден",
                author = "Джек Лондон",
                category = "Роман",
                rating = 0,
                totalPages = 480,
                currentPage = 0,
                status = ReadingStatus.PLANNED
            ),

            Book(
                id = 3,
                title = "Ночь в Лиссабоне",
                author = "Эрих Мария Ремарк",
                category = "Роман",
                rating = 0,
                totalPages = 300,
                currentPage = 0,
                status = ReadingStatus.PLANNED
            )
        )
    }
}