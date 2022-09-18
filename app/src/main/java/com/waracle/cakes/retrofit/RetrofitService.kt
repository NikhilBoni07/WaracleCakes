package com.waracle.cakes.retrofit

import com.waracle.cakes.model.Cakes
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("waracle_cake-android-client")
    fun getAllCakes(): Call<List<Cakes>>

    companion object {
        //Retruns the instance of retrofit client
        fun getInstance(): RetrofitService {
            var retrofitService: RetrofitService? = null
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://gist.githubusercontent.com/t-reed/739df99e9d96700f17604a3971e701fa/raw/1d4dd9c5a0ec758ff5ae92b7b13fe4d57d34e1dc/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}