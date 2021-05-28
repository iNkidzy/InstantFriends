package com.example.instantfriends.data

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.util.concurrent.Executors

class FriendRepository private constructor(private val context: Context){
    private val database: InstantFriendDatabase = Room.databaseBuilder(context.applicationContext,
        InstantFriendDatabase::class.java,
        "instantFriend-database").build()

    private val friendDao = database.friendDAO()

    fun getAll(): LiveData<List<BEFriend>> = friendDao.getAll()

    fun getByid(id: Int) = friendDao.getById(id)

    private val executor = Executors.newSingleThreadExecutor()

    fun insert(p: BEFriend){
        executor.execute{ friendDao.insert(p)}
    }

    fun update(p: BEFriend){
        executor.execute{ friendDao.update(p)}
    }

    fun delete(p: BEFriend){
        executor.execute{ friendDao.delete(p)}
    }

    fun clear(){
        executor.execute{ friendDao.deleteAll()}
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        private var Instance: FriendRepository? = null

        fun initialize(context: Context){
            if(Instance == null)
                Instance = FriendRepository(context)
        }

        fun get(): FriendRepository {
            if (Instance  != null) return Instance!!
            throw IllegalStateException("Friend repo not initialized")
        }
    }
}