package com.waracle.cakes.repository

import com.waracle.cakes.retrofit.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllCakes() = retrofitService.getAllCakes()
}