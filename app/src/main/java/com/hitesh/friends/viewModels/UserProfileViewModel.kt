package com.hitesh.friends.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserProfileViewModel: ViewModel() {
    val username = MutableLiveData<String>("")
    val firstName = MutableLiveData<String>("")
    val lastName = MutableLiveData<String>("")
    val profilePicUrl = MutableLiveData<String>("")

    init {
        viewModelScope.launch(IO) {
           Thread.sleep(1000)
           withContext(Main) {
               username.value = "Hitesh"
               firstName.value = "Hitesh"
               lastName.value = "Lalwani"
               profilePicUrl.value = "https://www.learningcontainer.com/wp-content/uploads/2020/07/Sample-JPEG-Image-File-Download-scaled.jpg"
           }
        }
    }

    fun submitForm() {
       Log.d("userprofile", "the form submitted with ${username.value} ${firstName.value} ${lastName.value} ${profilePicUrl.value}")
    }
}