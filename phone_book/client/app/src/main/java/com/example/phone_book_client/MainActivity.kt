package com.example.phone_book_client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phone_book_client.Constants.BASE_URL
import com.example.phone_book_client.adapter.MemberAdapter
import com.example.phone_book_client.databinding.ActivityMainBinding
import com.example.phone_book_client.entity.MembersDto
import com.example.phone_book_client.service.MemberService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var memberAdapter: MemberAdapter
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRetrofit()
        initRecyclerView()
        getMembers()

        binding.addMemberButton.setOnClickListener {
            val intent = Intent(this, MemberAddActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun initRecyclerView() {

        memberAdapter = MemberAdapter()

        binding.memberRecyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = memberAdapter
        }

    }

    private fun getMembers() {

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