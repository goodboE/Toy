package com.example.phone_book_client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.phone_book_client.databinding.ItemMemberBinding
import com.example.phone_book_client.entity.MemberDto

class MemberAdapter : ListAdapter<MemberDto, MemberAdapter.ViewHolder>(diffUtil){

    inner class ViewHolder(private val binding: ItemMemberBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(memberDto: MemberDto) {
            binding.memberNameTextView.text = memberDto.name
            binding.memberPhoneNumberTextView.text = memberDto.phoneAddress
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberAdapter.ViewHolder {
        return ViewHolder(ItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MemberAdapter.ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MemberDto>() {
            override fun areItemsTheSame(oldItem: MemberDto, newItem: MemberDto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MemberDto, newItem: MemberDto): Boolean {
                return oldItem == newItem
            }
        }
    }
}