package com.example.phone_book_client

import android.util.Log
import com.example.phone_book_client.adapter.MemberAdapter
import com.example.phone_book_client.entity.MembersDto
import com.example.phone_book_client.service.MemberService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MemberManager {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getMembers(memberAdapter: MemberAdapter) {

        val memberService = retrofit.create(MemberService::class.java)
        memberService.getMembers()
            .enqueue(object : Callback<MembersDto> {
                override fun onResponse(call: Call<MembersDto>, response: Response<MembersDto>) {

                    // todo 예외 처리
                    response.body()?.let {
                        Log.d("success, members:", it.toString())

                        it.members.forEach { member ->
                            Log.d("member -> ", member.toString())
                        }

                        memberAdapter.submitList(it.members)

                    }

                }

                override fun onFailure(call: Call<MembersDto>, t: Throwable) { Log.e("onFailure", t.toString()) }
            })
    }
}