package com.mariii.readdiary.data.api

data class GoogleBooksResponse(
    val items: List<BookItem>?
)

data class BookItem(
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(

    val title: String?,

    val authors: List<String>?,

    val pageCount: Int?,

    val categories: List<String>?,

    val imageLinks: ImageLinks?
)

data class ImageLinks(

    val thumbnail: String?
)