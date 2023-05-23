package com.example.roomapp.repository

import com.example.roomapp.model.GeoLocation
import com.example.roomapp.network.MyApi
import com.example.roomapp.util.BaseRemoteRepository
import com.example.roomapp.util.RemoteErrorEmitter

class LoginRemoteRepository(private val api:MyApi):BaseRemoteRepository(),LoginRepository.RemoteRepository {
    override suspend fun getGeoLocation(
        remoteErrorEmitter: RemoteErrorEmitter,
        req: GeoLocation.Req
    ): Any? {
        return safeApiCall(remoteErrorEmitter){api.getGeoLocation(req)}
    }
}