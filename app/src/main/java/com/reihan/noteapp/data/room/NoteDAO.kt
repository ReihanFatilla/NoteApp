package com.reihan.noteapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDAO {

    @Insert
    suspend fun addNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM table_note")
    fun getAllNote(): LiveData<List<Note>>

    @Query("DELETE FROM table_note")
    fun deleteAllNote()

    @Query("SELECT * FROM table_note WHERE title LIKE :querySearch")
    fun searchNoteByQuery(querySearch: String) : LiveData<List<Note>>

    @Query("SELECT * FROM table_note ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority() : LiveData<List<Note>>

    @Query("SELECT * FROM table_note ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority() : LiveData<List<Note>>
}