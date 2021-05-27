package com.example.instantfriends

import android.content.Intent
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.instantfriends.data.BEFriend
import com.example.instantfriends.data.FriendRepository
import com.example.instantfriends.data.observeOnce
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FriendsActivity: AppCompatActivity() {
    private var cache: List<BEFriend>? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)
        setupDataObserver()

        val details: FloatingActionButton = findViewById(R.id.fab5)
        val posts: FloatingActionButton = findViewById(R.id.fab4)

        // Fab button main post page
        findViewById<FloatingActionButton>(R.id.fab4).setOnClickListener { view ->
            val intent = Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

        // Fab button add friend
        findViewById<FloatingActionButton>(R.id.fab5).setOnClickListener { view ->
            val intent = Intent(this, DetailsActivity::class.java).also {
                startActivity(it)
            }
        }


        val fab = findViewById<View>(R.id.fab1) as ImageButton
        fab.setOnClickListener {
            fab.isSelected = !fab.isSelected
            fab.setImageResource(if (fab.isSelected) R.drawable.ic_heart else R.drawable.ic_add)
            val drawable = fab.drawable
            if (drawable is Animatable) {
                (drawable as Animatable).start()
            }
            if (fab.isSelected) {
                posts.show()
                details.show()
            } else {
                posts.hide()
                details.hide()
            }

        }
    }

    private fun setupDataObserver() {
        val lvNames = findViewById<ListView>(R.id.ListView)
        val mRep = FriendRepository.get()
        val nameObserver = Observer<List<BEFriend>>{ friends ->
            cache = friends;
            val asStrings = friends.map { f -> "${f.id}, ${f.name}"}
            val adapter: ListAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                asStrings.toTypedArray()
            )
            lvNames.adapter = adapter
        }
        mRep.getAll().observe(this, nameObserver)
        lvNames.onItemClickListener = AdapterView.OnItemClickListener { _, _, pos, _ -> onClickFriend(pos)}
    }

    private fun onClickFriend(pos: Int) {
        val id = cache!![pos].id
        val friendObserver = Observer<BEFriend> { friend ->
            if  (friend != null)
            {
                val intent = Intent(this, DetailsActivity::class.java).also {
                    it.putExtra("friendId", id)
                    startActivity(it)
                }
            }
        }
        val mRep = FriendRepository.get()
        mRep.getByid(id).observeOnce(this, friendObserver)
    }
}