package com.toy.blog_room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toy.blog_room.databinding.ItemPokemonBinding

class PokeAdapter(private val pokeList: MutableList<Pokemon>) :
    RecyclerView.Adapter<PokeAdapter.PokeViewHolder>() {

    class PokeViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            binding.nameTextView.text = pokemon.name
            binding.levelTextView.text = "Lv.%d".format(pokemon.level.toInt())
            binding.typeChip.text = pokemon.type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        holder.bind(pokeList[position])
    }

    override fun getItemCount(): Int {
        return pokeList.size
    }
}