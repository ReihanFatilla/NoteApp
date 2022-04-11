package com.reihan.noteapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.reihan.noteapp.data.NoteRepository
import com.reihan.noteapp.data.room.Note
import com.reihan.noteapp.data.room.NoteDAO
import com.reihan.noteapp.data.room.NoteDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application : Application): AndroidViewModel(application) {

    private val noteDao: NoteDAO = NoteDB.getDatabase(application).noteDao()
    private val repository: NoteRepository = NoteRepository(noteDao)

    fun getNoteData(): LiveData<List<Note>> = repository.getNoteData

    val sortByHighPriority: LiveData<List<Note>> = repository.sortByHighPriority
    val sortByLowPriority: LiveData<List<Note>> = repository.sortByLowPriority

    fun insertNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
        repository.insertNote(note)
        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteNote(note)
        }
    }

    fun deleteAllNote(){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteAllNote()
        }
    }

    fun updateNote(note: Note){
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }

    fun searchNoteQuery(query: String): LiveData<List<Note>>{
        return repository.searchNoteByQuery(query)
    }

}