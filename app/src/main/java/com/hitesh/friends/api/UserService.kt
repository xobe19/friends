package com.hitesh.friends.api

import com.hitesh.friends.models.Status
import com.hitesh.friends.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("/getUser")
    suspend fun getUser(@Query("username") username: String): Response<User>

    @GET("/createUser")
    suspend fun createUser(@Query("username") username: String, @Query("firstName") firstName: String, @Query("lastName") lastName: String, @Query("email") email: String, @Query("password") password: String): Response<Status>
}