package com.example.instantfriends

import android.content.Intent
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.instantfriends.data.BEFriend
import com.example.instantfriends.data.BEPost
import com.example.instantfriends.data.FriendRepository
import com.example.instantfriends.data.PostRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private var cache: List<BEPost>? = null;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newPost: FloatingActionButton = findViewById(R.id.fab2)
        val friends: FloatingActionButton = findViewById(R.id.fab3)

        // Fab button create post
         findViewById<FloatingActionButton>(R.id.fab2).setOnClickListener { view ->
             val intent = Intent(this, CreatePostActivity::class.java).also {
                 startActivity(it)
             }
         }

        // Fab button open friends
        findViewById<FloatingActionButton>(R.id.fab3).setOnClickListener { view ->
            val intent = Intent(this, FriendsActivity::class.java).also {
                startActivity(it)
            }
        }

        val fab = findViewById<View>(R.id.fab) as ImageButton
        fab.setOnClickListener {
            fab.isSelected = !fab.isSelected
            fab.setImageResource(if (fab.isSelected) R.drawable.ic_heart else R.drawable.ic_add)
            val drawable = fab.drawable
            if (drawable is Animatable) {
                (drawable as Animatable).start()
            }
            if(fab.isSelected){
                friends.show()
                newPost.show()
            } else {
                friends.hide()
                newPost.hide()
            }

        }
        //setupDataObserver()

            //Initialized local DB's
            FriendRepository.initialize(this)
            PostRepository.initialize(this)
        }

    private fun setupDataObserver() {
        val lvPosts = findViewById<ListView>(R.id.ListView)
        val mRep = PostRepository.get()
        val nameObserver = Observer<List<BEPost>>{ post ->
            cache = post;
            val asString = post.map { f -> "${f.description}"}
            val adapter: ListAdapter = ArrayAdapter(
                this,
                android.R.layout.activity_list_item,
                asString.toTypedArray()
            )
            lvPosts.adapter = adapter
        }
        mRep.getAll().observe(this, nameObserver)
    }

}