package com.example.roomapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.roomapp.data.RegisterDao
import com.example.roomapp.data.RegisterDatabase
import com.example.roomapp.model.GeoLocation
import com.example.roomapp.model.Register
import com.example.roomapp.repository.LoginRepository
import com.example.roomapp.repository.RegisterRepository
import com.example.roomapp.util.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginViewModel(private val loginRepository: LoginRepository):BaseViewModel(){

    var userNName:String = ""
    var passWWord:String = ""


        init {
//            val logiDao = RegisterDatabase.getInstance(application).registerDao
//            val checkUser: LiveData<List<Register>> = logiDao.getUser(userNName)
        }

    val  showMessage = MutableLiveData<String>()
    public fun geoLocationRequest(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){

                val loginReq =  GeoLocation.SIDTO(
                    apkVersion= "5.0.5",
                    apkVersionCode = "94",
                    osVersion = "10",
                    dbVersion = "31",
                    fpLoginType = "0",
                    password ="8FC91B6BE7914A5BF6E03A735223A9EA9FF6816A1EECB52C7B0D8DC29C550975",
                    androidId = "b96057fcbd2b2752",
                    employeeCode = "punf000179",
                    Lattitude = "0",
                    Longitude = "0",
                    modelName = "Google Android SDK built for x86_29",
                    ipAddress = "255.177.148.251",
                    browserName = "",
                    requireAnnouncement =true
                )

                val req =
                    GeoLocation.Req(loginReq)
                val LoginRes  = loginRepository.getGeoLocation(this@LoginViewModel, req = req)
                showMessage.postValue(
                    LoginRes.toString())

            }
        }
    }




}