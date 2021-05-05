package com.example.instantfriends.data

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.util.concurrent.Executors

class PostRepository private constructor(private val context: Context) {

    private val database: InstantFriendDatabase = Room.databaseBuilder(context.applicationContext,
        InstantFriendDatabase::class.java,
        "instantFriend-database").build()

    private val postDao = database.postDAO()

    fun getAll(): LiveData<List<BEPost>> = postDao.getAll()

    fun getByid(id: Int) = postDao.getById(id)

    private val executor = Executors.newSingleThreadExecutor()

    fun insert(p: BEPost){
        executor.execute{ postDao.insert(p)}
    }

    fun update(p: BEPost){
        executor.execute{ postDao.update(p)}
    }

    fun delete(p: BEPost){
        executor.execute{ postDao.delete(p)}
    }

    fun clear(){
        executor.execute{ postDao.deleteAll()}
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        private var Instance: PostRepository? = null

        fun initialize(context: Context){
            if(Instance == null)
                Instance = PostRepository(context)
        }

        fun get(): PostRepository {
            if (Instance  != null) return Instance!!
            throw IllegalStateException("Post repo not initialized")
        }
    }
}