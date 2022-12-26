package com.hitesh.friends.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hitesh.friends.models.Posts
import com.hitesh.friends.models.PostsItem
import com.hitesh.friends.repository.PostsRepository
import com.hitesh.friends.repository.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedViewModelFactory(val repository: PostsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FeedViewModel(repository = repository) as T
    }
}

class FeedViewModel(repository: PostsRepository): ViewModel() {
val posts : MutableLiveData<List<PostsItem>> = MutableLiveData<List<PostsItem>>()

init {
    viewModelScope.launch(IO) {
        val fetchedPosts =  repository.getAllPosts()

withContext(Main) {
    posts.value = fetchedPosts
}

    }
}

}