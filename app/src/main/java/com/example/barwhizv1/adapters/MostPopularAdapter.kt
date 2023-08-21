package com.example.barwhizv1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.barwhizv1.databinding.PopularItemsBinding
import com.example.barwhizv1.model.CategoryDrinks

class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.PopularCocktailViewHolder>() {
    lateinit var onItemClick:((CategoryDrinks) -> Unit)
    private var drinksList = ArrayList<CategoryDrinks>()

    fun setDrinks(drinksList:ArrayList<CategoryDrinks>){
        this.drinksList = drinksList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularCocktailViewHolder {
        return PopularCocktailViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PopularCocktailViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(drinksList[position].strDrinkThumb)
            .into(holder.binding.imgPopularCocktailItem)

        holder.itemView.setOnClickListener{
            onItemClick.invoke(drinksList[position])
        }
    }

    override fun getItemCount(): Int {
       return drinksList.size
    }

    class PopularCocktailViewHolder( val binding:PopularItemsBinding):ViewHolder(binding.root)
}