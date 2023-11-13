package kh.edu.rupp.ite.memo.api

import kh.edu.rupp.ite.memo.models.UserRequest
import kh.edu.rupp.ite.memo.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {

    @POST("/users/signin")
    suspend fun signUp(@Body userRequest: UserRequest): Response<UserResponse>

    @POST("/users/signin")
    suspend fun signIn(@Body userRequest: UserRequest): Response<UserResponse>

}