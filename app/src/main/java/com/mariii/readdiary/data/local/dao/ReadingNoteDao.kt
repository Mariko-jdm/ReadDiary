package com.mariii.readdiary.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mariii.readdiary.data.local.entity.ReadingNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReadingNoteDao {

    @Query("SELECT * FROM reading_notes WHERE bookId = :bookId")
    fun getNotesForBook(bookId: Int): Flow<List<ReadingNoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: ReadingNoteEntity)

    @Delete
    suspend fun deleteNote(note: ReadingNoteEntity)
}