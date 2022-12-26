package com.hitesh.friends

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.hitesh.friends.api.ImageUrlService
import com.hitesh.friends.api.PostsService
import com.hitesh.friends.api.RetrofitHelper
import com.hitesh.friends.api.UserService
import com.hitesh.friends.database.LocalUserDatabase
import com.hitesh.friends.models.Posts
import com.hitesh.friends.repository.PostsRepository
import com.hitesh.friends.repository.UserRepository
import com.hitesh.friends.viewModels.FeedViewModel
import com.hitesh.friends.viewModels.FeedViewModelFactory
import com.hitesh.friends.viewModels.UserProfileViewModel
import com.hitesh.friends.viewModels.UserProfileViewModelFactory
import kotlinx.coroutines.launch

class AppActivity : AppCompatActivity() {
    lateinit var feedViewModel: FeedViewModel
    lateinit var userProfileViewModel: UserProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var localUserDao = LocalUserDatabase.getDatabase(applicationContext).localUserDao()
        var imageUrlService = RetrofitHelper.getInstance2().create(ImageUrlService::class.java)
        var postsService = RetrofitHelper.getInstance().create(PostsService::class.java)
        var postsRepository = PostsRepository(postsService, localUserDao, imageUrlService)
         var userRepository: UserRepository
        userRepository = UserRepository(userService = RetrofitHelper.getInstance().create(UserService::class.java), localUserDao  )
        feedViewModel = ViewModelProvider(this, FeedViewModelFactory(postsRepository)).get(FeedViewModel::class.java)
        userProfileViewModel = ViewModelProvider(this, UserProfileViewModelFactory(userRepository)).get(UserProfileViewModel::class.java)
       setContent {
           var scaffoldState = rememberScaffoldState()
           var scope = rememberCoroutineScope()
           var navController = rememberNavController()
          var currentNavPosition = remember { mutableStateOf("feed") }
           Scaffold(scaffoldState = scaffoldState,topBar = {
                             TopAppBar() {
                      Button(onClick = {
                         scope.launch {
                            scaffoldState.drawerState.open()
                         }
                      }) {
                   Text("drawer button")
                      }
                             }
           },drawerContent = {

               Column() {
                  Button(onClick = {
                      navController.navigate("feed")
                      currentNavPosition.value = "feed"
                      scope.launch {
                         scaffoldState.drawerState.close()
                      }
                  }, colors = ButtonDefaults.buttonColors(((if (currentNavPosition.value == "feed") (Color.White) else (Color.Blue)) as Color) )) {
                      Text("feed")
                  }
                   Button(onClick = {
                       navController.navigate("profile")
                       currentNavPosition.value = "profile"
                       scope.launch {

                           scaffoldState.drawerState.close()
                       }

                   }, colors = ButtonDefaults.buttonColors(((if (currentNavPosition.value == "profile") (Color.White) else (Color.Blue)) as Color) )) {
                       Text("profile")
                   }
                   Button(onClick = {
                       createPost()
                   }) {
                       /* TODO put the button at last*/
                       Text("create post")
                   }
               }

           }) {
               NavHost(navController = navController, startDestination = "feed" ) {
                   composable("feed") {
                       FeedScreen()
                   }
                   composable("profile") {
                       ProfileDetailsScreen()
                   }
               }
           }
       }
    }
    @Composable
    fun FeedScreen() {
        var posts = feedViewModel.posts.observeAsState()
        if(posts.value != null) {
            LazyColumn {

                items(((posts.value) as List<Posts>).size) {
                    Card(
                        content = {
                           Column {
                              Text(posts.value!![it].username)
                               Text(posts.value!![it].postContent)
                               AsyncImage(model = posts.value!![it].postImage, contentDescription = null)
                           }

                        }
                    )
                }
            }
        }
        else {
           Text("...loading")
        }
    }

    @Composable
    fun ProfileDetailsScreen() {
        var imageUrl = userProfileViewModel.profilePicUrl.observeAsState()
        var username = userProfileViewModel.username.observeAsState()
        var firstName = userProfileViewModel.firstName.observeAsState()
        var lastName = userProfileViewModel.lastName.observeAsState()

        Column {

            AsyncImage(model = imageUrl.value, contentDescription = null)
            TextField(value = username.value ?: "", onValueChange = {}, enabled = false)
            TextField(value = firstName.value ?: "", onValueChange = {
                userProfileViewModel.firstName.value = it
            })
            TextField(value = lastName.value ?: "", onValueChange = {
                userProfileViewModel.lastName.value = it
            })
           Button(onClick = {
    userProfileViewModel.submitForm()
           })  {
               Text("submit")
           }

        }
    }

    private fun createPost() {
        val intent: Intent = Intent(this,CreatePostActivity::class.java).also {
            it.putExtra("post_content", "sample post content")
        }
        startActivity(intent)
    }
}