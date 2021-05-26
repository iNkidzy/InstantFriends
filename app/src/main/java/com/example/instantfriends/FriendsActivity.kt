package com.example.instantfriends

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.instantfriends.data.BEFriend
import com.example.instantfriends.data.FriendRepository
import com.example.instantfriends.data.observeOnce

class FriendsActivity: AppCompatActivity() {
    private var cache: List<BEFriend>? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)
        setupDataObserver()
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
    fun onClickNewFriend(view: View){
        val intent = Intent(this, DetailsActivity::class.java).also {
            startActivity(it)
        }

    }
}