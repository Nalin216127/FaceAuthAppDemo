package com.example.roomapp.util

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException


abstract class BaseRemoteRepository {
    companion object {
        private const val TAG = "BaseRemoteRepository"
        private const val MESSAGE_KEY = "message"
        private const val ERROR_KEY = "error"
    }

    suspend inline fun <T> safeApiCall(
        emitter: RemoteErrorEmitter,
        crossinline callFunction: suspend () -> T
    ): T? {
        return try {
            val myObject = withContext(Dispatchers.IO) {
//                delay(5000) for testing
                callFunction.invoke()
            }

            myObject
        } catch (e: Exception) {
            withContext(Dispatchers.Main){
                e.printStackTrace()
                Log.e("BaseRemoteRepo", "Call error : ${e.localizedMessage}",e.cause )
            }
            null
        }
    }

    fun getErrorMessage(responseBody: ResponseBody?):String{
        return try {
            val jsonObject = JSONObject(responseBody!!.string())
            when{
                jsonObject.has(MESSAGE_KEY)->jsonObject.getString(MESSAGE_KEY)
                jsonObject.has(ERROR_KEY)->jsonObject.getString(ERROR_KEY)
                else-> "Something wrong happened"
            }
        } catch (e: Exception) {
            "Something wrong happened"
        }
    }

}