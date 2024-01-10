package com.example.phone_book_client

import android.content.Intent
import android.os.Build.VERSION_CODES.M
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phone_book_client.Constants.BASE_URL
import com.example.phone_book_client.adapter.MemberAdapter
import com.example.phone_book_client.databinding.ActivityMemberAddBinding
import com.example.phone_book_client.entity.MemberDto
import com.example.phone_book_client.service.MemberService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MemberAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMemberAddBinding
    private lateinit var memberAdapter: MemberAdapter
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRetrofit()


        binding.addMemberButton.setOnClickListener {

            val memberText = binding.nameEditText.text.toString()
            val phoneAddressText = binding.phoneAddressEditText.text.toString()

            val memberDto = MemberDto(memberText, phoneAddressText);
            val memberService = retrofit.create(MemberService::class.java)
            memberService.addMember(memberDto)
                .enqueue(object : Callback<MemberDto> {
                    override fun onResponse(call: Call<MemberDto>, response: Response<MemberDto>) {

                        memberAdapter = MemberAdapter()
                        MemberManager.getMembers(memberAdapter)
                        Log.d("success", memberAdapter.currentList.toString())
                    }

                    override fun onFailure(call: Call<MemberDto>, t: Throwable) {}
                })

            finish()
        }


    }

    private fun initRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}