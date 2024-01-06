package com.example.phone_book_client

import android.content.Intent
import android.os.Build.VERSION_CODES.M
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.phone_book_client.Constants.BASE_URL
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
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberAddBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initRetrofit()

        binding.addMemberButton.setOnClickListener {

            val memberText = binding.nameEditText.text.toString()
            val phoneAddressText = binding.phoneAddressEditText.text.toString()

            // todo add to server
            val memberDto = MemberDto(memberText, phoneAddressText);
            val memberService = retrofit.create(MemberService::class.java)
            memberService.addMember(memberDto)
                .enqueue(object : Callback<MemberDto> {
                    override fun onResponse(call: Call<MemberDto>, response: Response<MemberDto>) {
                        Log.d("addMember", memberDto.toString())
                    }

                    override fun onFailure(call: Call<MemberDto>, t: Throwable) {}
                })

            Log.d("addMember: memberText = ", memberText.toString())
            Log.d("addMember: phoneAddress = ", phoneAddressText.toString())

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