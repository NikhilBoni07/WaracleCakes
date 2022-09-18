package com.waracle.cakes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.waracle.cakes.R
import com.waracle.cakes.databinding.AdapterCakeBinding
import com.waracle.cakes.model.Cakes

class CakeAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var cakes = mutableListOf<Cakes>()

    fun setAllCakes(cakes: List<Cakes>) {
        this.cakes = cakes.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterCakeBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val cake = cakes[position]
        holder.binding.xtxtCakesName.setText(cake.title)
        Glide.with(holder.itemView.context)
            .load(cake.image)
            .placeholder(R.drawable.ic_image_default)
            .error(R.drawable.ic_image_default)
            .into(holder.binding.ximgxCakes)
    }

    override fun getItemCount(): Int {
        return cakes.size
    }
}

class MainViewHolder(val binding: AdapterCakeBinding) : RecyclerView.ViewHolder(binding.root) {

}