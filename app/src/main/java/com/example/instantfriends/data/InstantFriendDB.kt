package com.example.instantfriends.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BEPost::class], version=1)
abstract class InstantFriendDatabase : RoomDatabase() {

    abstract fun postDAO(): PostDAO
}