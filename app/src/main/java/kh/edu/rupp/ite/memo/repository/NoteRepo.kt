package kh.edu.rupp.ite.memo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kh.edu.rupp.ite.memo.api.NoteAPI
import kh.edu.rupp.ite.memo.models.NoteRequest
import kh.edu.rupp.ite.memo.models.NoteResponse
import kh.edu.rupp.ite.memo.utils.NetworkResponse
import org.json.JSONObject
import javax.inject.Inject

class NoteRepo @Inject constructor(private val noteAPI: NoteAPI) {
    private val _notesLiveData = MutableLiveData<NetworkResponse<List<NoteResponse>>>()
    val notesLiveData : LiveData<NetworkResponse<List<NoteResponse>>>
    get() = _notesLiveData

    private val _statusLiveData = MutableLiveData<NetworkResponse<Pair<Boolean, String>>>()
    val statusLiveData get() = _statusLiveData

    suspend fun getNotes() {
        _notesLiveData.postValue(NetworkResponse.Loading())
        val response = noteAPI.getNotes()
        if (response.isSuccessful && response.body() != null) {
            _notesLiveData.postValue(NetworkResponse.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _notesLiveData.postValue(NetworkResponse.Error(errorObj.getString("message")))
        } else {
            _notesLiveData.postValue(NetworkResponse.Error("Something Went Wrong"))
        }
    }


    suspend fun createNote(noteRequest: NoteRequest) {
    }
    suspend fun updateNote(noteId : String , noteRequest: NoteRequest) {
    }
    suspend fun deleteNote(noteId: String) {
    }
}

