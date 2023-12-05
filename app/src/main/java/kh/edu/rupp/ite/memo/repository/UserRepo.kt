package kh.edu.rupp.ite.memo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kh.edu.rupp.ite.memo.api.UserAPI
import kh.edu.rupp.ite.memo.models.UserRequest
import kh.edu.rupp.ite.memo.models.UserResponse
import kh.edu.rupp.ite.memo.utils.NetworkResponse
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject


class UserRepo @Inject constructor(private val userAPI: UserAPI) {

    private val _userResponseLiveData = MutableLiveData<NetworkResponse<UserResponse>>()
    val userResponseLiveData: LiveData<NetworkResponse<UserResponse>>
        get() = _userResponseLiveData

    suspend fun registerUser(userRequest: UserRequest) {
        _userResponseLiveData.postValue(NetworkResponse.Loading())
        val response = userAPI.signUp(userRequest)
        handleResponse(response)
    }


    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userResponseLiveData.postValue(NetworkResponse.Success(response.body()!!))
        }
        else if(response.errorBody()!=null){
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponseLiveData.postValue(NetworkResponse.Error(errorObj.getString("message")))
        }
        else{
            _userResponseLiveData.postValue(NetworkResponse.Error("Something Went Wrong"))
        }
    }
}