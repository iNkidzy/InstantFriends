package com.example.instantfriends

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.instantfriends.data.BEFriend
import com.example.instantfriends.data.FriendRepository

class DetailsActivity: AppCompatActivity() {

    private var friendLoaded = false
    private var friendID = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val friendId = intent.getIntExtra("friendId", -1)
        if (friendId >= 0) {
            friendLoaded = true
            friendID = friendId
            val mRep = FriendRepository.get()
            val friendName: TextView = findViewById(R.id.nameField)
            val friendPhone: TextView = findViewById(R.id.numberField)
            val friendEmail: TextView = findViewById(R.id.emailField)
            val saveFriend: Button = findViewById(R.id.saveFriend)
            val callButton: Button = findViewById(R.id.callButton)
            val messageButton: Button = findViewById(R.id.messageButton)
            val emailButton: Button = findViewById(R.id.emailButton)
            val deleteFriend: Button = findViewById(R.id.deleteFriend)

            val nameObserver = Observer<BEFriend> { friend ->
                friendName.text = friend.name
                friendPhone.text = friend.phoneNumber
                friendEmail.text = friend.email
            }
            callButton.visibility = View.VISIBLE
            messageButton.visibility = View.VISIBLE
            emailButton.visibility = View.VISIBLE
            saveFriend.text = "Update Friend"
            deleteFriend.visibility = View.VISIBLE
            mRep.getByid(friendId).observe(this, nameObserver)
        }
    }

    private fun onClickSaveFriend(view: View){
        val mRep = FriendRepository.get()
        val friendName: TextView = findViewById(R.id.nameField)
        val friendPhone: TextView = findViewById(R.id.numberField)
        val friendEmail: TextView = findViewById(R.id.emailField)

        val friend = BEFriend(friendID,
        friendName.text.toString(),
        friendPhone.text.toString(),
        friendEmail.text.toString())

        if(friendLoaded){
            mRep.update(friend)
            Toast.makeText(this, "${friend.name} has been updated", Toast.LENGTH_SHORT).show()
            finish()
        }
        else{
            mRep.insert(friend)
            Toast.makeText(this, "${friend.name} has been created", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun onClickDeleteFriend(view: View){
        val mRep = FriendRepository.get()
        val friendName: TextView = findViewById(R.id.nameField)
        val friendPhone: TextView = findViewById(R.id.numberField)
        val friendEmail: TextView = findViewById(R.id.emailField)

        val friend = BEFriend(friendID,
            friendName.text.toString(),
            friendPhone.text.toString(),
            friendEmail.text.toString())

        mRep.delete(friend)
        finish()
    }
    private fun onClickCall(view: View){
        val friendPhone: TextView = findViewById(R.id.numberField)
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${friendPhone.text}")
        startActivity(intent)
    }
    private fun onClickEmail(view: View) {
        val friendEmail: TextView = findViewById(R.id.emailField)
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type="plain/text"
        val receivers = arrayOf("${friendEmail.text}")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, receivers)
        emailIntent.putExtra(Intent.EXTRA_TEXT,
            "Hey, this email is sent to you by the app I just created")
        startActivity(emailIntent)
    }
    private fun onClickMessage(view: View){
        val friendNumber: TextView = findViewById(R.id.numberField)
        val sendIntent = Intent(Intent.ACTION_VIEW)
        sendIntent.data=Uri.parse("sms:${friendNumber.text}")
        sendIntent.putExtra("sms_body", "This is magic!")
        startActivity(sendIntent)
    }


}