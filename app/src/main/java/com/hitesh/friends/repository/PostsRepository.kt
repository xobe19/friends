package com.hitesh.friends.repository

import android.net.Uri
import com.hitesh.friends.api.ImageUrlService
import com.hitesh.friends.api.PostsService
import com.hitesh.friends.database.LocalUserDao
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.URI

class PostsRepository(val postsService: PostsService, val localUserDao: LocalUserDao, val imageUrlService: ImageUrlService) {
    suspend fun createNewPost(postContent: String, postImage: Uri) {
        val username = localUserDao.getAllUsers().get(0).username
        val file: File = File(URI.create(postImage.path))
        val body = MultipartBody.Part.createFormData("image", "post_image.jpg", RequestBody.create(
            "image/*".toMediaTypeOrNull(), file))
       val resp = imageUrlService.uploadImage(body).body()
        postsService.createPost(username = username, postContent = postContent, postImage = resp?.data?.display_url!!)
    }
}