package com.hitesh.friends.repository

import android.content.ContentResolver
import android.net.Uri
import com.hitesh.friends.api.ImageUrlService
import com.hitesh.friends.api.PostsService
import com.hitesh.friends.database.LocalUserDao
import com.hitesh.friends.models.Posts
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.URI

class PostsRepository(val postsService: PostsService, val localUserDao: LocalUserDao, val imageUrlService: ImageUrlService) {
    suspend fun createNewPost(postContent: String, file: File) {
        val username = localUserDao.getAllUsers().get(0).username
        val body = MultipartBody.Part.createFormData("image", "post_image.jpg", RequestBody.create(
            "image/*".toMediaTypeOrNull(), file))
       val resp = imageUrlService.uploadImage(body, "1824d0409c3c5ff130a2dec6172e33ef").body()
        postsService.createPost(username = username, postContent = postContent, postImage = resp?.data?.display_url!!)

    }
    suspend fun getAllPosts(): Posts {
        val username = localUserDao.getAllUsers().get(0).username
       return postsService.getPosts(username).body()!!
    }


}