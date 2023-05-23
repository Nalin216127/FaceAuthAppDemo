package com.example.roomapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.roomapp.data.RegisterDao
import com.example.roomapp.model.GeoLocation
import com.example.roomapp.model.Register
import com.example.roomapp.network.MyApi
import com.google.android.gms.common.internal.FallbackServiceBroker
import java.util.concurrent.Flow


class RegisterRepository(private val registerDao:RegisterDao) {
    val logAll:LiveData<List<Register>> = registerDao.getAllUsers()

    suspend fun insert(uregister:Register){
        registerDao.insert(uregister)
    }

//    suspend fun isValidUser(userName: String): Register{
//
//        return registerDao.isValidUser(userName = userName)
//
//    }

    /*suspend fun getGeoLocation():Any{
        return api.getGeoLocation(req = GeoLocation.Req(
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
        ))
    }*/

//    fun createCSV(){
//        val sb = StringBuilder()
//        var afterFirst = false
//        sb.append("{POSTDATALOCAL}")
//        for(s in registerDao.getLocalCsv()){
//            if(afterFirst) sb.append(",")
//            afterFirst = true
//            sb.append(s)
//        }
//    }

}