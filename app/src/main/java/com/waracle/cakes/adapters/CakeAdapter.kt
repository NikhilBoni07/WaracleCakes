package com.waracle.cakes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.waracle.cakes.R
import com.waracle.cakes.databinding.AdapterCakeBinding
import com.waracle.cakes.model.Cakes

class CakeAdapter : RecyclerView.Adapter<CakeAdapter.MainViewHolder>() {

    var cakes = mutableListOf<Cakes>()
    var onItemClick: ((Cakes) -> Unit)? = null

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

        holder.binding.xrvxParentLyt.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.translate)

        /*holder.binding.xrvxParentLyt.setOnClickListener(View.OnClickListener {
            Toast.makeText(holder.itemView.context, cake.desc, Toast.LENGTH_SHORT).show()
            showPopUpDesc(cake.desc, holder.itemView.context);
        })*/
    }


    override fun getItemCount(): Int {
        return cakes.size
    }

    inner class MainViewHolder(val binding: AdapterCakeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val xrvxParentLyt: RelativeLayout = binding.xrvxParentLyt

        init {
            xrvxParentLyt.setOnClickListener {
                onItemClick?.invoke(cakes[adapterPosition])
            }
        }
    }
}
