package com.bylaew.exytepokeapi.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bylaew.exytepokeapi.R
import com.bylaew.exytepokeapi.model.PokemonById.PokemonById
import com.bylaew.exytepokeapi.utils.Constants
import kotlinx.android.synthetic.main.pokemon_item.view.*

class FavouritePokemonAdapter(
    private val context: Context
) :
    RecyclerView.Adapter<FavouritePokemonAdapter.FavouriteViewHolder>() {

    private lateinit var favoriteList: List<PokemonById>
    inner class FavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<PokemonById>(){
        override fun areItemsTheSame(oldItem: PokemonById, newItem: PokemonById): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: PokemonById, newItem: PokemonById): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.pokemon_item, parent, false)

        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        favoriteList = differ.currentList
        val favouritePokemon = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this)
                .load(Constants.BASE_IMG_URL + "${favouritePokemon.id}.png")
                .thumbnail(0.25f)
                .into(holder.itemView.imageView)
            tvName.text = favouritePokemon.name

        }


        //TODO unlike -> remove from database!!


    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

}