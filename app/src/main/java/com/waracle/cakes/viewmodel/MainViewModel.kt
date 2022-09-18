package com.waracle.cakes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.waracle.cakes.model.Cakes
import com.waracle.cakes.repository.MainRepository
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(val repository: MainRepository) : ViewModel() {

    var cakeList = MutableLiveData<List<Cakes>>()
    var errorMessage = MutableLiveData<String>()

    fun getAllCakes() {
        val response = repository.getAllCakes()
        response.enqueue(object : Callback<List<Cakes>> {
            override fun onResponse(call: Call<List<Cakes>>, response: Response<List<Cakes>>) {
                cakeList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Cakes>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}