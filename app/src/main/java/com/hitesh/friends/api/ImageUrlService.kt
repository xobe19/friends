package com.hitesh.friends.api

import com.hitesh.friends.models.ImgbbResp
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import java.io.File

interface ImageUrlService {
    @POST("/1/upload")
    @Multipart
     suspend fun uploadImage(@Part image: MultipartBody.Part, @Query("key")key: String ): Response<ImgbbResp>


}