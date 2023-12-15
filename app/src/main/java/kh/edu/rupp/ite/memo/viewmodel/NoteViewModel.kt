package kh.edu.rupp.ite.memo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kh.edu.rupp.ite.memo.models.NoteRequest
import kh.edu.rupp.ite.memo.repository.NoteRepo
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepo: NoteRepo) : ViewModel() {

    val notesLiveData get() = noteRepo.notesLiveData
    val statusLiveData get() = noteRepo.statusLiveData

    fun createNote(noteRequest: NoteRequest) {
        viewModelScope.launch {
            noteRepo.createNote(noteRequest)
        }
    }

    fun getAllNotes() {
        viewModelScope.launch {
            noteRepo.getNotes()
        }
    }

    fun updateNote(id: String, noteRequest: NoteRequest){
        viewModelScope.launch {
            noteRepo.updateNote(id, noteRequest)
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            noteRepo.deleteNote(noteId)
        }
    }




}