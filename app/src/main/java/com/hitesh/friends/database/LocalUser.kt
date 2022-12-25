package com.hitesh.friends.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local_user_table")
data class LocalUser(@PrimaryKey(autoGenerate = true) val id: Int, val username: String)