
package com.hitesh.friends.api

import com.hitesh.friends.models.Posts
import com.hitesh.friends.models.Status
import com.hitesh.friends.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostsService {
    @GET("/getPosts")
    suspend fun getUser(@Query("username") username: String): Response<Posts>

    @GET("/createPost")
    suspend fun createPost(@Query("username") username: String, @Query("postContent") postContent: String, @Query("postImage") postImage: String): Response<Status>
}
