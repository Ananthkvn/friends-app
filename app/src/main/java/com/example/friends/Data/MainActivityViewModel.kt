package com.example.friends.Data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel() {
    lateinit var createNewUserLiveData: MutableLiveData<User?>

    init {
        createNewUserLiveData = MutableLiveData()
    }

    fun getCreateNewUserObserver(): MutableLiveData<User?> {
        return createNewUserLiveData
    }

    fun createNewUser(user: User) {
        val retroService =
            ServiceBuilder.getRetrofitInstance().create(FormsApiInterface::class.java)
        val call = retroService.createUser(user)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
    createNewUserLiveData.postValue(response.body())
                } else {
                    createNewUserLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                createNewUserLiveData.postValue(null)
            }

        })
    }
}

