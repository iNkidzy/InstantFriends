package com.example.instantfriends.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FriendDAO {

@Query("SELECT * from BEFriend order by id")
fun getAll(): LiveData<List<BEFriend>>

@Query("SELECT * from BEFriend where id = (:id)")
fun getById(id: Int): LiveData<BEFriend>

@Insert
fun insert(f: BEFriend)

@Update
fun update(f: BEFriend)

@Delete
fun delete(f: BEFriend)

@Query("DELETE from BEFriend")
fun deleteAll()
}