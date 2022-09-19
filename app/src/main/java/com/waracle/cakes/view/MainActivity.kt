package com.waracle.cakes.view

import android.content.Context
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.waracle.cakes.R
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
    private var retrofitService = RetrofitService.getInstance(this)

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

        binding.swipeRefreshLayout.setOnRefreshListener {
            // on below line we are setting is refreshing to false.
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.getAllCakes()
        }

        viewModel =
            ViewModelProvider(this, MainViewModelFactory(MainRepository(retrofitService))).get(
                MainViewModel::class.java
            )

        setObservers()
        setOnClickListners()
        viewModel.getAllCakes()

    }

    private fun setOnClickListners() {
        cakeAdapter.onItemClick = {
            showPopUpDesc(it.desc)
        }
    }


    fun setObservers() {
        viewModel.cakeList.observe(this, Observer {
            Log.i(TAG, "Cake List $it")
            val list = it.sortedBy { it.title } // Order entries by name
                .distinct()         // Remove duplicate entries
            cakeAdapter.setAllCakes(list)
        })

        viewModel.errorMessage.observe(this, Observer {
            Log.i(TAG, "Cake List API call error $it")
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun showPopUpDesc(desc: String?) {
        // Initialize a new layout inflater instance
        val inflater: LayoutInflater = this.getSystemService(
            Context.LAYOUT_INFLATER_SERVICE
        ) as LayoutInflater
        // Inflate a custom view using layout inflater
        val view = inflater.inflate(R.layout.popup_view, null)

        // Initialize a new instance of popup window
        val popupWindow = PopupWindow(
            view, // Custom view to show in popup window
            CoordinatorLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
            CoordinatorLayout.LayoutParams.WRAP_CONTENT // Window height
        )
        popupWindow.isFocusable = true

        // Set an elevation for the popup window
        popupWindow.elevation = 10.0F

        // Slide animation for popup window enter transition
        val slideIn = Slide()
        slideIn.slideEdge = Gravity.TOP
        popupWindow.enterTransition = slideIn

        // Slide animation for popup window exit transition
        val slideOut = Slide()
        slideOut.slideEdge = Gravity.END
        popupWindow.exitTransition = slideOut

        // Get the widgets reference from custom view
        val cakeDesc = view.findViewById<TextView>(R.id.xtxtxCakeDesc)
        val buttonClose = view.findViewById<Button>(R.id.xbtnxClose)

        cakeDesc.setText(desc)
        buttonClose.setOnClickListener(View.OnClickListener {
            popupWindow.dismiss()
        })

        // Finally, show the popup window on app
        TransitionManager.beginDelayedTransition(binding.swipeRefreshLayout)
        popupWindow.showAtLocation(
            binding.swipeRefreshLayout,
            Gravity.CENTER, // Layout position to display popup
            0, // X offset
            0 // Y offset
        )
    }
}