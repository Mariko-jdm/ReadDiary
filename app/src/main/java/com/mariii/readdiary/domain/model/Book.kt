package com.mariii.readdiary.domain.model

data class Book(
    val id: Int = 0,
    val title: String,
    val author: String,
    val category: String = "",
    val rating: Int = 0,
    val totalPages: Int = 0,
    val currentPage: Int = 0,
    val status: ReadingStatus = ReadingStatus.PLANNED,
    val notes: List<ReadingNote> = emptyList(),
    val coverUri: String = ""
) {
    val progressText: String
        get() = "$currentPage из $totalPages стр."

    val progressPercent: Int
        get() = if (totalPages > 0) {
            (currentPage * 100) / totalPages
        } else {
            0
        }
}