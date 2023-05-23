package com.example.roomapp.util

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel:ViewModel(),RemoteErrorEmitter {
    val mutableErrorMessage = MutableLiveData<String>()

    override fun onError(msg: String) {
        mutableErrorMessage.postValue(msg)
    }
}