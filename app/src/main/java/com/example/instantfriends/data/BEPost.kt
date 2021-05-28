package com.example.instantfriends.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BEPost(
    @PrimaryKey(autoGenerate = true) var id:Int,
    var description: String,
    var photoPath: String,
) {

}