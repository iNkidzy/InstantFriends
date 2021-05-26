package com.example.instantfriends

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.instantfriends.data.BEFriend
import com.example.instantfriends.data.FriendRepository
import com.example.instantfriends.data.PostRepository
import com.example.instantfriends.data.observeOnce
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    var cache: List<BEFriend>? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Fab button
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val intent = Intent(this, CreatePostActivity::class.java).also {
                startActivity(it)
            }}
        //Fab button
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.background = null
        navView.menu.getItem(1).isEnabled = false

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_friends))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //Initialized local DB's
        FriendRepository.initialize(this)
        PostRepository.initialize(this)

       // setupDataObserver()

    }

    //loads all friends from DB and sets up the listview with entities loaded
    private fun setupDataObserver(){
        //Load the list fragment for friend here, dont think this is done right
        val lvNames = findViewById<ListView>(R.id.navigation_friends)
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
    private fun onClickFriend(pos: Int){
        val id = cache!![pos].id
        val friendObserver = Observer<BEFriend> { friend ->
            if  (friend != null)
            {
                val intent = Intent(this, DetailsActivity::class.java).also {
                    it.putExtra("position", id)
                    startActivity(it)
                }
            }
        }
        val mRep = FriendRepository.get()
        mRep.getByid(id).observeOnce(this,friendObserver)
    }


    private fun onClickNewFriend(view: View){
        val intent = Intent(this, DetailsActivity::class.java).also {
            startActivity(it)
        }

    }
}