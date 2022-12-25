package com.hitesh.friends.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [LocalUser::class], version = 1)
abstract class LocalUserDatabase: RoomDatabase() {
    abstract fun localUserDao(): LocalUserDao

    companion object {
        private var instance: LocalUserDatabase? = null

        fun getDatabase(context: Context): LocalUserDatabase {
           if(instance == null) {
               synchronized(this) {
                instance = Room.databaseBuilder(context, LocalUserDatabase::class.java, "localuser_db").build()
               }
           }
            return instance!!
        }
    }
}