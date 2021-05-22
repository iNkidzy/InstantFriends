package com.example.instantfriends.ui.home

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is the PostPage"
    }

    val text: LiveData<String> = _text
}