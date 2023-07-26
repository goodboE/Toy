package com.toy.fcam_voca

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toy.fcam_voca.databinding.ItemWordBinding

class WordAdapter
    (val list: MutableList<WordModel>,
    private val wordClickListener: WordClickListener? = null,
) : RecyclerView.Adapter<WordAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WordModel) {
            binding.wordTextView.text = item.word
            binding.meanTextView.text = item.mean
            binding.typeChip.text = item.type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemWordBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val word = list[position]
        holder.itemView.setOnClickListener {
            wordClickListener?.onClick(word)
        }

        return holder.bind(word)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface WordClickListener {
        fun onClick(word: WordModel)
    }


}