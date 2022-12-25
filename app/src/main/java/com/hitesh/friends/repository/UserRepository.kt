package com.hitesh.friends.repository

import androidx.lifecycle.LiveData
import com.hitesh.friends.api.UserService
import com.hitesh.friends.database.LocalUser
import com.hitesh.friends.database.LocalUserDao
import com.hitesh.friends.models.Status
import com.hitesh.friends.models.User

class UserRepository(val userService: UserService, val localUserDao: LocalUserDao) {

   suspend fun loggedIn(): Boolean {
return localUserDao.getAllUsers().isNotEmpty()
   }

   suspend fun createUser(username: String, firstName: String, lastName: String, email: String, password: String): Status {
      val status =   userService.createUser( username, firstName, lastName, email, password)?.body() ?: Status(false)
      if(status.success) {
     localUserDao.insertLocalUser(LocalUser(34, username = username))
      }
      return status
   }

   suspend fun getUser(): User {
     return  userService.getUser(localUserDao.getAllUsers().get(0).username)?.body()!!
   }


}