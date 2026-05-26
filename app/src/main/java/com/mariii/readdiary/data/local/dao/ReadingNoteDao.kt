package com.mariii.readdiary.data.local.dao

//@Dao
//interface ReadingNoteDao {
//
//    @Query("SELECT * FROM reading_notes WHERE bookId = :bookId")
//    fun getNotesForBook(bookId: Int): Flow<List<ReadingNoteEntity>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertNote(note: ReadingNoteEntity)
//
//    @Update
//    suspend fun updateNote(note: ReadingNoteEntity)
//
//    @Delete
//    suspend fun deleteNote(note: ReadingNoteEntity)
//}