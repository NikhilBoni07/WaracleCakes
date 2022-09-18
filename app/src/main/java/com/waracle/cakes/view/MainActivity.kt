package com.waracle.cakes.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.waracle.cakes.adapters.CakeAdapter
import com.waracle.cakes.databinding.ActivityMainBinding
import com.waracle.cakes.repository.MainRepository
import com.waracle.cakes.retrofit.RetrofitService
import com.waracle.cakes.viewmodel.MainViewModel
import com.waracle.cakes.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var retrofitService = RetrofitService.getInstance()

    val cakeAdapter = CakeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.xrvxCakeList.adapter = cakeAdapter
        binding.xrvxCakeList.addItemDecoration(         // will display a divider between each entry
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        viewModel =
            ViewModelProvider(this, MainViewModelFactory(MainRepository(retrofitService))).get(
                MainViewModel::class.java
            )

        setObservers()

        viewModel.getAllCakes()

    }

    fun setObservers() {
        viewModel.cakeList.observe(this, Observer {
            Log.i(TAG, "Cake List $it")
            val list = it.sortedBy { it.title } // Order entries by name
                .distinct()         // Remove duplicate entries
            cakeAdapter.setAllCakes(list)
        })

        viewModel.errorMessage.observe(this, Observer {
            Log.i(TAG, "Cake List API Error $it")
        })
    }
}