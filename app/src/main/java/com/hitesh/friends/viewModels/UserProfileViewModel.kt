package com.hitesh.friends.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hitesh.friends.repository.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserProfileViewModelFactory(val repository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserProfileViewModel(repository = repository) as T
    }
}

class UserProfileViewModel(private val repository: UserRepository): ViewModel() {
    val username = MutableLiveData<String>("")
    val firstName = MutableLiveData<String>("")
    val lastName = MutableLiveData<String>("")
    val profilePicUrl = MutableLiveData<String>("")

    init {
        viewModelScope.launch(IO) {
         val user =  repository.getUser()

           withContext(Main) {
               username.value = user.username
               firstName.value = user.firstName
               lastName.value = user.lastName
               profilePicUrl.value = "https://i.ibb.co/RSzn3y2/image.png"
           }
        }
    }
    fun submitForm() {
        Log.d("userprofile", "the form submitted with ${username.value} ${firstName.value} ${lastName.value} ${profilePicUrl.value}")
       viewModelScope.launch(IO) {

          repository.updateUser(username.value!!, firstName.value!!, lastName.value!!)
       }

    }

}