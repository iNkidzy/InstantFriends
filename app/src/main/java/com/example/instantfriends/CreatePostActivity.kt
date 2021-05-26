package com.example.instantfriends

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instantfriends.data.BEPost
import com.example.instantfriends.data.PostRepository
import com.google.android.material.textfield.TextInputEditText

class CreatePostActivity : AppCompatActivity(){

    private var imgPath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_post)
    }

    //Permissions for camera

    private fun clickNewPost(view: View){
        val mRep = PostRepository.get()
        val postDescription: TextInputEditText = findViewById(R.id.postDescription)

        if(imgPath != "") {
            val post = BEPost(
                0,
                imgPath,
                postDescription.text.toString()
            )
            mRep.insert(post)
            Toast.makeText(this, "Your post has been created", Toast.LENGTH_SHORT).show()
            finish()
        }
        else{
            Toast.makeText(this, "Please take a photo first", Toast.LENGTH_SHORT).show()
        }
    }


}