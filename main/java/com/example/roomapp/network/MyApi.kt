package com.example.roomapp.network

import com.example.roomapp.BuildConfig
import com.example.roomapp.model.GeoLocation
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface MyApi {
    @POST("CommonLoginServiceAndroid10")
    suspend fun getGeoLocation(@Body req: GeoLocation.Req):Any

    companion object{
        lateinit var okkHttpclient:OkHttpClient

        operator fun invoke(): MyApi {
//            val proxy = Proxy(Proxy.Type.SOCKS, InetSocketAddress("107.108.8.187",8080))
            val interceptor = run {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.apply {
                    if ( BuildConfig.DEBUG) {
                        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    }else {
                        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
                    }
                }
            }

                okkHttpclient = OkHttpClient.Builder()
//                    .addInterceptor(ConnectivityInterceptor(App.appContext))
//                                            .addInterceptor(AuthInterceptor())
//                    .proxy(proxy)
                    .addInterceptor(interceptor)
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}