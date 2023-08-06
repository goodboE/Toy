package com.toy.fishshapedbread

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.toy.fishshapedbread.databinding.ItemFishBinding

class FishAdapter(private val fishList: MutableList<Fish>) : RecyclerView.Adapter<FishAdapter.FishViewHolder>() {

    private var onCreateCount = 1
    private var onBindCount = 1

    fun allClick() {
        fishList.forEach { Fish ->
            Fish.isChecked = true
        }
        notifyDataSetChanged()
    }

    // 붕어빵 틀 정의 (설계도)=> 모양은 ItemFishBinding, bind 라는 기능을 가진 붕어빵 틀
    class FishViewHolder(private val binding: ItemFishBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fish: Fish) {
            binding.readBeanRateTextView.text = "%d%%".format(fish.readBeanRate.toInt())
            binding.creamRateTextView.text = "%d%%".format(fish.creamRate.toInt())
            binding.checkBox.isChecked = fish.isChecked
        }
    }

    // 설계도대로 붕어빵 틀을 만들어서 손님들 앞에 진열
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FishViewHolder {
        Log.d("FishAdapter", "onCreateViewHolder => $onCreateCount")
        onCreateCount++
        val binding = ItemFishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FishViewHolder(binding).also { holder ->
            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                fishList[holder.adapterPosition].isChecked = isChecked
                Toast.makeText(parent.context, "Checked!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 붕어빵 틀 안에 속을 채워 넣음
    override fun onBindViewHolder(holder: FishViewHolder, position: Int) {
        Log.d("FishAdapter", "onBindViewHolder => $onBindCount")
        onBindCount++
        holder.bind(fishList[position])
    }

    override fun getItemCount(): Int {
        return fishList.size
    }
}