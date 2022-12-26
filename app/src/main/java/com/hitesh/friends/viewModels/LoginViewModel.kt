package com.hitesh.friends.viewModels

import android.content.Intent
import android.util.Log
import androidx.lifecycle.*
import androidx.navigation.NavController
import com.hitesh.friends.repository.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModelFactory(val repository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(userRepository = repository) as T
    }
}

class LoginViewModel(val userRepository: UserRepository): ViewModel() {
    val tag = "LoginViewModel";
    var userEmail: MutableLiveData<String> = MutableLiveData("")
    var userPassword: MutableLiveData<String> = MutableLiveData("")
    var userFirstName: MutableLiveData<String> = MutableLiveData("")
    var userLastName: MutableLiveData<String> = MutableLiveData("")
    var username: MutableLiveData<String> = MutableLiveData("")
    var loggedin: MutableLiveData<Boolean> = MutableLiveData(false)

   init {
       viewModelScope.launch(Main) {

           loggedin.value = userRepository.loggedIn()
       }
   }

    fun submitForm() {
        Log.d(tag, "login submitted with ${userEmail.value} and ${userPassword.value} and ${userFirstName.value} & ${userLastName.value}")
        viewModelScope.launch(IO) {
            val status = userRepository.createUser(username.value!!, userFirstName.value!!, userLastName.value!!, userEmail.value!!, userPassword.value!!)
            Log.d(tag, if(status.success) "yep" else "no" ?: "hi")
            withContext(Main) {
                loggedin.value = status.success
            }
        }
    }

}