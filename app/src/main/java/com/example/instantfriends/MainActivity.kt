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

        val mRep = PostRepository.get()
        val posts: List<BEPost>? = mRep.getAll().value
        if(posts == null){
            Toast.makeText(this, "its null af", Toast.LENGTH_LONG).show()
        }else
        Toast.makeText(this, posts!![0].description, Toast.LENGTH_LONG).show()
        /*val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = CustomAdapter(this, posts!!)*/
        setupDataObserver()
        }

    private fun setupDataObserver() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val mRep = PostRepository.get()
        val postObserver = Observer<List<BEPost>>{ post ->
            cache = post;
            val asPosts = post.map { p -> BEPost(p.id,p.description,p.photoPath) }
            val adapter = CustomAdapter(
                this,
                asPosts
            )
            recyclerView.adapter = adapter
        }
        mRep.getAll().observe(this, postObserver)
    }

}