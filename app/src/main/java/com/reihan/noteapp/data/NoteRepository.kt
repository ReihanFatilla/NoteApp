package com.reihan.noteapp.data

import androidx.lifecycle.LiveData
import com.reihan.noteapp.data.room.Note
import com.reihan.noteapp.data.room.NoteDAO

class NoteRepository(private val noteDAO: NoteDAO) {

    val getNoteData: LiveData<List<Note>> = noteDAO.getAllNote()

    val sortByHighPriority: LiveData<List<Note>> = noteDAO.sortByHighPriority()
    val sortByLowPriority: LiveData<List<Note>> = noteDAO.sortByLowPriority()

    suspend fun insertNote(note: Note){
        noteDAO.addNote(note)
    }

    fun searchNoteByQuery(query: String) : LiveData<List<Note>>{
        return noteDAO.searchNoteByQuery(query)
    }

    fun deleteAllNote(){
        noteDAO.deleteAllNote()
    }

    suspend fun deleteNote(note: Note){
        noteDAO.deleteNote(note)
    }

    suspend fun updateNote(note: Note){
        noteDAO.updateNote(note)
    }
}