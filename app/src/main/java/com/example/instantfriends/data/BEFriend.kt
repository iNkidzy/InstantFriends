package com.example.instantfriends.data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BEFriend(
    @PrimaryKey(autoGenerate = true) var id:Int,
    var name: String,
    var phoneNumber: String,
    var email: String
)  {
}