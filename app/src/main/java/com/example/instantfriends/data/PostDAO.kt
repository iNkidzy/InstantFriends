package com.example.instantfriends.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PostDAO {
    @Query("SELECT * from BEPost order by id")
    fun getAll(): LiveData<List<BEPost>>

    @Query("SELECT * from BEPost where id = (:id)")
    fun getById(id: Int): LiveData<BEPost>

    @Insert
    fun insert(f: BEPost)

    @Update
    fun update(f: BEPost)

    @Delete
    fun delete(f: BEPost)

    @Query("DELETE from BEPost")
    fun deleteAll()
}