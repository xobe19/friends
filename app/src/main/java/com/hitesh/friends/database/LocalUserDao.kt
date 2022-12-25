package com.hitesh.friends.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LocalUserDao {
    @Insert
    suspend fun insertLocalUser(user: LocalUser)

    @Query("select * from local_user_table")
    suspend fun getAllUsers(): List<LocalUser>

}