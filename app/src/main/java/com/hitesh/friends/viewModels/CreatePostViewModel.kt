package com.hitesh.friends.viewModels

import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.FileUtils.copy
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.hitesh.friends.database.LocalUserDao
import com.hitesh.friends.repository.PostsRepository
import com.hitesh.friends.repository.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream


class CreatePostViewModelFactory(val repository: PostsRepository, val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreatePostViewModel(postsRepository = repository, application) as T
    }
}

class CreatePostViewModel(val postsRepository: PostsRepository, application: Application): AndroidViewModel(application) {
    val postContent = MutableLiveData<String>("")
    val postImage = MutableLiveData<Uri>(Uri.EMPTY)

    fun uploadPost() {
        viewModelScope.launch(IO) {
            withContext(Main) {
            }
            postsRepository.createNewPost(postContent = postContent.value!!, file= fileFromContentUri(getApplication<Application>().applicationContext, postImage.value!!))
        }
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    fun fileFromContentUri(context: Context, contentUri: Uri): File {
        // Preparing Temp file name
        val fileExtension = "jpg"
        val fileName = "temp_file" + if (fileExtension != null) ".$fileExtension" else ""

        // Creating Temp file
        val tempFile: File = File(context.cacheDir, fileName)
        tempFile.createNewFile()

        try {
            val oStream = FileOutputStream(tempFile)
            val inputStream = context.contentResolver.openInputStream(contentUri)

            inputStream?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    copy(inputStream, oStream)
                } else {
                    TODO("VERSION.SDK_INT < Q")
                }
            }

            oStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return tempFile
    }

}