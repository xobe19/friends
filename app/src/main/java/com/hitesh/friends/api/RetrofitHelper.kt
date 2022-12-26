package com.hitesh.friends.api

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    val base_url = "https://friends-api-xi.vercel.app/"
    val base_url2 = "https://api.imgbb.com/"
    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create()).build()
    }
    fun getInstance2(): Retrofit {
        return Retrofit.Builder().baseUrl(base_url2).addConverterFactory(GsonConverterFactory.create()).build()
    }
}