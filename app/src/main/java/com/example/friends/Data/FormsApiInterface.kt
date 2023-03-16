package com.example.friends.Data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FormsApiInterface {

    @POST("register")
    @Headers("Content-Type: application/json")
    fun createUser(@Body params: User): Call<User>
}