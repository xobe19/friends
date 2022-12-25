package com.hitesh.friends.viewModels

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel: ViewModel() {
    val tag = "LoginViewModel";
    var userEmail: MutableLiveData<String> = MutableLiveData("")
    var userPassword: MutableLiveData<String> = MutableLiveData("")
    var userFirstName: MutableLiveData<String> = MutableLiveData("")
    var userLastName: MutableLiveData<String> = MutableLiveData("")
    var username: MutableLiveData<String> = MutableLiveData("")
    var loggedin: MutableLiveData<Boolean> = MutableLiveData(false)

    fun submitForm() {
        Log.d(tag, "login submitted with ${userEmail.value} and ${userPassword.value} and ${userFirstName.value} & ${userLastName.value}")
        viewModelScope.launch(IO) {
           Thread.sleep(1000)
            withContext(Main) {
                loggedin.value = true
            }
        }
    }

}