package com.hitesh.friends.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitesh.friends.models.Posts
import com.hitesh.friends.models.PostsItem
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedViewModel: ViewModel() {
val posts : MutableLiveData<List<PostsItem>> = MutableLiveData<List<PostsItem>>()

init {
    viewModelScope.launch(IO) {
        Thread.sleep(1000)
withContext(Main) {
    posts.value = listOf(
        PostsItem(
            username = "hitesh",
            postContent = "hlo",
            postImage = "https://www.learningcontainer.com/wp-content/uploads/2020/07/Sample-JPEG-Image-File-Download-scaled.jpg"
        ),
        PostsItem(
            username = "test238",
            postContent = "hlooo",
            postImage = "https://www.learningcontainer.com/wp-content/uploads/2020/07/Sample-JPEG-Image-File-Download-scaled.jpg"
        )
    )
}

    }
}

}