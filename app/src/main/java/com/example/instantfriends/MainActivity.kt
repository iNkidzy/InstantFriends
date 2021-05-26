package com.example.instantfriends

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.instantfriends.data.BEFriend
import com.example.instantfriends.data.FriendRepository
import com.example.instantfriends.data.PostRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity() {

    var cache: List<BEFriend>? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Fab button
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val intent = Intent(this, CreatePostActivity::class.java).also {
                startActivity(it)
            }
        }
        //Fab button

            //Initialized local DB's
            FriendRepository.initialize(this)
            PostRepository.initialize(this)


        }

    fun tempFriends(view: View) {}




}