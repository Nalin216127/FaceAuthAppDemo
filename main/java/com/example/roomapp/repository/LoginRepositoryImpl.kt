package com.example.roomapp.repository

import com.example.roomapp.model.GeoLocation
import com.example.roomapp.util.RemoteErrorEmitter

class LoginRepositoryImpl(private val remoteRepo:LoginRepository.RemoteRepository):
    LoginRepository {
     override suspend fun getGeoLocation(
        remoteErrorEmitter: RemoteErrorEmitter,
        req: GeoLocation.Req
    ): Any? {
        val response = remoteRepo.getGeoLocation(remoteErrorEmitter,req)
        return  response?.toString()
    }


}