package com.hitesh.friends

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hitesh.friends.api.ImageUrlService
import com.hitesh.friends.api.PostsService
import com.hitesh.friends.api.RetrofitHelper
import com.hitesh.friends.database.LocalUserDatabase
import com.hitesh.friends.repository.PostsRepository
import com.hitesh.friends.viewModels.CreatePostViewModel
import com.hitesh.friends.viewModels.CreatePostViewModelFactory
import retrofit2.create


class CreatePostActivity : AppCompatActivity() {
    val tag = "CreatePostActivity"
    lateinit var createPostViewModel: CreatePostViewModel
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Log.d(tag, ActivityResult.resultCodeToString(result.resultCode))
        if (result.resultCode == Activity.RESULT_OK) {
            Log.d(tag, "ok done!");
            // There are no request codes
            val intnt = result.data
           createPostViewModel.postImage.value =  intnt?.data
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val postsService = RetrofitHelper.getInstance().create(PostsService::class.java)
        val imageUrlService = RetrofitHelper.getInstance2().create(ImageUrlService::class.java)
        val localUserDao = LocalUserDatabase.getDatabase(applicationContext).localUserDao()
        val repo = PostsRepository(postsService, localUserDao, imageUrlService)
        createPostViewModel = ViewModelProvider(this, CreatePostViewModelFactory(repository = repo, application)).get(CreatePostViewModel::class.java)
        Log.d(tag, intent.getStringExtra("post_content") ?: "")

        setContent {
            CreatePostScreen()
        }
    }

    @Composable
    fun CreatePostScreen() {

        var postContent = createPostViewModel.postContent.observeAsState()
        var postImageUri = createPostViewModel.postImage.observeAsState()

        LaunchedEffect(key1 = Unit, block = {

    createPostViewModel.postContent.value = if ( intent.clipData != null)   intent.clipData?.getItemAt(0)?.coerceToText(applicationContext)
        .toString() else ""

        })
        Column(Modifier.verticalScroll(rememberScrollState())){
        TextField(
            modifier = Modifier.height(200.dp),
            value = postContent.value ?: "",
            onValueChange = {
                createPostViewModel.postContent.value = it
            })
        Button(onClick = {

            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT


            resultLauncher.launch(intent)

        }) {
            Text("upload image")
        }
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(data = postImageUri.value)
                .build(), contentDescription = null
        )
        Button(onClick = {createPostViewModel.uploadPost()}) {
            Text("upload")
        }
    }

    }
}