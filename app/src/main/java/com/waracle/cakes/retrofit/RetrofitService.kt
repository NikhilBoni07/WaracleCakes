package com.waracle.cakes.retrofit

import com.waracle.cakes.model.Cakes
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RetrofitService {

    fun getAllCakes(): Call<List<Cakes>>

    companion object {
        //Retruns the instance of retrofit client
        fun getInstance(): RetrofitService {
            var retrofitService: RetrofitService? = null
            if (retrofitService != null) {
                var retrofit = Retrofit.Builder()
                    .baseUrl("")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}