package com.hitesh.friends

import android.content.Intent
import android.os.Bundle
import android.telecom.Call.Details
import android.util.Log
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hitesh.friends.viewModels.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.log

class LoginActivity : ComponentActivity() {
    lateinit var loginViewModel: LoginViewModel
    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        setContent {
            navController = rememberNavController()
            var loggedin = loginViewModel.loggedin.observeAsState()

            LaunchedEffect(key1 = loggedin.value, block = {
               if(loggedin.value ?: false) {
                   val intent: Intent = Intent(applicationContext, AppActivity::class.java)
                   startActivity(intent)
               }
            })

            NavHost(navController = navController, startDestination = "login") {

                composable("login") {
                    LoginScreen()
                }
                composable("details") {
                    DetailsScreen()
                }
            }
            }
        }
    @Composable
    fun LoginScreen() {




var userEmail = loginViewModel.userEmail.observeAsState("")
var userPassword = loginViewModel.userPassword.observeAsState("")



var tfModifier: Modifier = Modifier
    .fillMaxWidth()
    .padding(20.dp)
        Scaffold(backgroundColor = Color.DarkGray, modifier = Modifier.fillMaxSize()) {

            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                TextField(modifier = tfModifier,value = userEmail.value, onValueChange = {
                    loginViewModel.userEmail.value = it
                })
                TextField(modifier = tfModifier, value = userPassword.value, onValueChange = {
                    loginViewModel.userPassword.value = it
                })
                Button(onClick = {

                    navController.navigate("details")

                }, colors = ButtonDefaults.buttonColors(Color.Cyan)) {
                   Text("Login/Signup")
                }
            }
        }
    }
   @Composable
    fun DetailsScreen() {
       var tfModifier: Modifier = Modifier
           .fillMaxWidth()
           .padding(20.dp)


       var userFirstName = loginViewModel.userFirstName.observeAsState("")
       var userLastName = loginViewModel.userLastName.observeAsState("")
        var username = loginViewModel.username.observeAsState("")

       Scaffold(backgroundColor = Color.DarkGray, modifier = Modifier.fillMaxSize()) {
//what do we call you
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                TextField(modifier = tfModifier,value = userFirstName.value, onValueChange = {
                    loginViewModel.userFirstName.value = it
                })
                TextField(modifier = tfModifier, value = userLastName.value, onValueChange = {
                    loginViewModel.userLastName.value = it
                })
                TextField(modifier = tfModifier, value = username.value, onValueChange = {
                    loginViewModel.username.value = it
                })
                Button(onClick = {

                                 loginViewModel.submitForm()

                }, colors = ButtonDefaults.buttonColors(Color.Cyan)) {
                    Text("Login/Signup")
                }
            }
        }
    }


}


