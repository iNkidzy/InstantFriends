package com.example.instantfriends

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.example.instantfriends.data.BEFriend
import com.example.instantfriends.data.FriendRepository
import com.google.android.material.textfield.TextInputEditText
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity: AppCompatActivity() {

    private var friendLoaded = false
    private var friendID = 0
    private val PERMISSION_REQUEST_CODE = 1
    val TAG = "xyz"

    private val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        checkPermissions()

        val friendId = intent.getIntExtra("friendId", -1)
        if (friendId >= 0) {
            friendLoaded = true
            friendID = friendId
            val mRep = FriendRepository.get()
            val friendName: TextView = findViewById(R.id.nameField)
            val friendPhone: TextView = findViewById(R.id.numberField)
            val friendEmail: TextView = findViewById(R.id.emailField)
            val saveFriend: Button = findViewById(R.id.saveFriend)
            val callButton: ImageButton = findViewById(R.id.callButton)
            val messageButton: ImageButton = findViewById(R.id.messageButton)
            val emailButton: ImageButton = findViewById(R.id.emailButton)
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

    private fun requestPermission(){
        if(!isPermissionGiven()){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                requestPermissions(permissions, 1)
        }
    }

    private fun isPermissionGiven(): Boolean {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return permissions.all {p -> checkSelfPermission(p) == PackageManager.PERMISSION_GRANTED}
        }
        return true
    }

    fun onClickSaveFriend(view: View){
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

    fun onClickDeleteFriend(view: View){
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

    fun onClickCall(view: View){
        val friendPhone: TextView = findViewById(R.id.numberField)
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${friendPhone.text}")
        startActivity(intent)
    }
    fun onClickEmail(view: View) {
        val friendEmail: TextView = findViewById(R.id.emailField)
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type="plain/text"
        val receivers = arrayOf("${friendEmail.text}")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, receivers)
        emailIntent.putExtra(Intent.EXTRA_TEXT,
            "Hey, this email is sent to you by the app I just created")
        startActivity(emailIntent)
    }
    fun onClickMessage(view: View){
        val friendNumber: TextView = findViewById(R.id.numberField)
        val sendIntent = Intent(Intent.ACTION_VIEW)
        sendIntent.data=Uri.parse("sms:${friendNumber.text}")
        sendIntent.putExtra("sms_body", "This is magic!")
        startActivity(sendIntent)
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
        val permissions = mutableListOf<String>()
        if ( ! isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE) ) permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if ( ! isGranted(Manifest.permission.CAMERA) ) permissions.add(Manifest.permission.CAMERA)
        if (permissions.size > 0)
            ActivityCompat.requestPermissions(this, permissions.toTypedArray(), PERMISSION_REQUEST_CODE)
    }
    private fun isGranted(permission: String): Boolean =
        ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    // return a new file with a timestamp name in a folder named [folder] in
    // the external directory for pictures.
    // Return null if the file cannot be created
    private fun getOutputMediaFile(folder: String): File? {
        // in an emulated device you can see the external files in /sdcard/Android/data/<your app>.
        val mediaStorageDir = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), folder)
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "failed to create directory")
                return null
            }
        }
        // Create a media file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val postfix = "jpg"
        val prefix = "IMG"
        return File(mediaStorageDir.path +
                File.separator + prefix +
                "_" + timeStamp + "." + postfix)
    }

}