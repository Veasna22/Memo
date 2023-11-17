package kh.edu.rupp.ite.memo.repository

import kh.edu.rupp.ite.memo.api.NoteAPI
import kh.edu.rupp.ite.memo.models.NoteResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepo(private val noteApi: NoteAPI) {

    suspend fun fetchNotes(): NoteResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val response = noteApi.getNotes()
                if (response.isSuccessful) {
                    return@withContext response.body()
                } else {
                    // Handle error
                    return@withContext null
                }
            } catch (e: Exception) {
                // Handle exception
                return@withContext null
            }
        }
    }
}
