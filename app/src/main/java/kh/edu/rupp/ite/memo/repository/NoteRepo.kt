package kh.edu.rupp.ite.memo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kh.edu.rupp.ite.memo.api.NoteAPI
import kh.edu.rupp.ite.memo.models.NoteRequest
import kh.edu.rupp.ite.memo.models.NoteResponse
import kh.edu.rupp.ite.memo.utils.NetworkResponse
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NoteRepo @Inject constructor(private val noteAPI: NoteAPI) {
    private val _notesLiveData = MutableLiveData<NetworkResponse<List<NoteResponse>>>()
    val notesLiveData : LiveData<NetworkResponse<List<NoteResponse>>>
    get() = _notesLiveData

    private val _statusLiveData = MutableLiveData<NetworkResponse<Pair<Boolean, String>>>()
    val statusLiveData get() = _statusLiveData

    suspend fun createNote(noteRequest: NoteRequest) {
        _statusLiveData.postValue(NetworkResponse.Loading())
        val res = noteAPI.createNote(noteRequest)
        handleResponse(res, "Note Created successfully")
    }

    suspend fun getNotes() {
        _notesLiveData.postValue(NetworkResponse.Loading())
        val res = noteAPI.getNotes()
        if (res.isSuccessful && res.body() != null) {
            _notesLiveData.postValue(NetworkResponse.Success(res.body()!!))
        } else if (res.errorBody() != null) {
            val errorObj = JSONObject(res.errorBody()!!.charStream().readText())
            _notesLiveData.postValue(NetworkResponse.Error(errorObj.getString("message")))
        } else {
            _notesLiveData.postValue(NetworkResponse.Error("Something Went Wrong"))
        }
    }

    suspend fun updateNote(id: String, noteRequest: NoteRequest) {
        _statusLiveData.postValue(NetworkResponse.Loading())
        val res = noteAPI.updateNote(id, noteRequest)
        handleResponse(res, "Note Updated successfully")
    }

    suspend fun deleteNote(noteId: String) {
        _statusLiveData.postValue(NetworkResponse.Loading())
        val res = noteAPI.deleteNote(noteId)
        handleResponse(res, "Note Deleted successfully")
    }

    private fun handleResponse(res: Response<NoteResponse>, message: String) {
        if (res.isSuccessful && res.body() != null) {
            _statusLiveData.postValue(NetworkResponse.Success(Pair(true, message)))
        } else {
            _statusLiveData.postValue(NetworkResponse.Success(Pair(false, "Something went wrong")))
        }
    }
}

