package com.example.phone_book_client.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.phone_book_client.Constants
import com.example.phone_book_client.MainActivity
import com.example.phone_book_client.MemberManager
import com.example.phone_book_client.R
import com.example.phone_book_client.databinding.ItemMemberBinding
import com.example.phone_book_client.entity.Member
import com.example.phone_book_client.entity.MemberDto
import com.example.phone_book_client.service.MemberService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MemberAdapter : ListAdapter<Member, MemberAdapter.ViewHolder>(diffUtil){

    inner class ViewHolder(private val binding: ItemMemberBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(member: Member) {
            binding.memberNameTextView.text = member.name
            binding.memberPhoneNumberTextView.text = member.phoneAddress
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberAdapter.ViewHolder {
        return ViewHolder(ItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("InflateParams")
    override fun onBindViewHolder(holder: MemberAdapter.ViewHolder, position: Int) {
        holder.bind(currentList[position])

        // todo delete, update
        holder.itemView.setOnClickListener {

            val member = currentList[position]
            Log.d("member", member.id.toString() + member.name + member.phoneAddress)

            val dialogView = LayoutInflater.from(it.context).inflate(R.layout.member_dialog, null)
            dialogView.findViewById<EditText>(R.id.dNameEditText).setText(member.name)
            dialogView.findViewById<EditText>(R.id.dPhoneAddressEditText)
                .setText(member.phoneAddress)

            val builder = AlertDialog.Builder(it.context)
            builder.setTitle("title")
            builder.setView(dialogView)

            builder.setPositiveButton("수정") { _, _ ->

                val changedName =
                    dialogView.findViewById<EditText>(R.id.dNameEditText).text.toString()
                val changedPAddress =
                    dialogView.findViewById<EditText>(R.id.dPhoneAddressEditText).text.toString()

                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val memberService = retrofit.create(MemberService::class.java)
                val call =
                    memberService.updateMember(member.id, MemberDto(changedName, changedPAddress))

                call.enqueue(object : Callback<MemberDto> {
                    override fun onResponse(call: Call<MemberDto>, response: Response<MemberDto>) {

                        Toast.makeText(holder.itemView.context, "수정되었습니다.", Toast.LENGTH_SHORT)
                            .show()

                        MemberManager.getMembers(this@MemberAdapter)
                    }

                    override fun onFailure(call: Call<MemberDto>, t: Throwable) {

                    }
                })


            }
            builder.setNegativeButton("삭제") { _, _ ->

                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val memberService = retrofit.create(MemberService::class.java)
                val call = memberService.deleteMember(member.id)

                call.enqueue(object : Callback<MemberDto> {
                    override fun onResponse(call: Call<MemberDto>, response: Response<MemberDto>) {
                        Toast.makeText(holder.itemView.context, "삭제되었습니다.", Toast.LENGTH_SHORT)
                            .show()
                        MemberManager.getMembers(this@MemberAdapter)
                    }

                    override fun onFailure(call: Call<MemberDto>, t: Throwable) {}
                })


            }

            builder.show()
        }


    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Member>() {
            override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean {
                return oldItem == newItem
            }
        }
    }
}