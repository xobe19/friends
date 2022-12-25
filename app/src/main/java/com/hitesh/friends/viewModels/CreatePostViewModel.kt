package com.hitesh.friends.viewModels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class CreatePostViewModel: ViewModel() {
    val postContent = MutableLiveData<String>("")
    val postImage = MutableLiveData<Uri>(Uri.EMPTY)

    fun uploadPost() {
    }

}