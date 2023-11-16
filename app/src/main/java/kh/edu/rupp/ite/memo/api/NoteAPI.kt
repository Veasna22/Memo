package kh.edu.rupp.ite.memo.api

import kh.edu.rupp.ite.memo.models.NoteResponse
import retrofit2.Response
import retrofit2.http.GET

interface NoteAPI {
    @GET("/notes")
    suspend fun getNotes(): Response<NoteResponse>
}