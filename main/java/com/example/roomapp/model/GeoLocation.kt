package com.example.roomapp.model

import com.google.gson.annotations.SerializedName

object GeoLocation {
    data class Req(val SIDTO:SIDTO )

        data class SIDTO(
            @SerializedName("apkVersion")
            val apkVersion:String,
            @SerializedName("ApkVersioncode")
            val apkVersionCode:String,
            @SerializedName("OSVersion")
            val osVersion:String,
            @SerializedName("DBversion")
            val dbVersion:String,
            @SerializedName("FPLoginType")
            val fpLoginType:String,
            @SerializedName("password")
            val password:String,
            @SerializedName("AndroidID")
            val androidId:String,
            @SerializedName("EmployeeCode")
            val employeeCode:String,
            @SerializedName("Lattitude")
            val Lattitude:String,
            @SerializedName("Longitude")
            val Longitude:String,
            @SerializedName("ModelName")
            val modelName:String,
            @SerializedName("IPAddress")
            val ipAddress:String,
            @SerializedName("BrowserName")
            val browserName:String,
            @SerializedName("RequireAnnouncement")
            val requireAnnouncement:Boolean
        )

}