package com.example.roomapp.repository

import com.example.roomapp.model.GeoLocation
import com.example.roomapp.util.RemoteErrorEmitter

interface LoginRepository {
    suspend fun getGeoLocation(remoteErrorEmitter: RemoteErrorEmitter, req:GeoLocation.Req):Any?

    interface RemoteRepository{
        suspend  fun getGeoLocation(remoteErrorEmitter: RemoteErrorEmitter, req : GeoLocation.Req): Any?
    }
}
