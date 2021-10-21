package com.bylaew.exytepokeapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bylaew.exytepokeapi.R
import com.bylaew.exytepokeapi.model.Pokemon.PokemonResult
import com.bylaew.exytepokeapi.utils.Constants.Companion.BASE_IMG_URL
import kotlinx.android.synthetic.main.pokemon_item.view.*
import java.util.*


class PokemonAdapter(val context: Context) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<PokemonResult>(){
        override fun areItemsTheSame(oldItem: PokemonResult, newItem: PokemonResult): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: PokemonResult, newItem: PokemonResult): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item,parent,false)
        return PokemonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val currentPokemonItem = differ.currentList[position]

        holder.itemView.apply {
            tvName.text = currentPokemonItem.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
            Glide.with(this)
                .load(BASE_IMG_URL+"${position+1}.png")
                .thumbnail(0.25f)
                .into(imageView)
            setOnClickListener {
                onItemClickListener?.let { it(position) }
            }
        holder.itemView.saveButton.setOnClickListener {
            it.saveButton.setImageResource(R.drawable.ic_baseline)

        }

        }

    }

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

}