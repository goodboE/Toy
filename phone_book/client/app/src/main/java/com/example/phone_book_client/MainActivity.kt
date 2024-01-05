package com.example.phone_book_client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phone_book_client.adapter.MemberAdapter
import com.example.phone_book_client.databinding.ActivityMainBinding
import com.example.phone_book_client.entity.MembersDto
import com.example.phone_book_client.service.MemberService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Member

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var memberAdapter: MemberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        getBuilder()
    }

    private fun initRecyclerView() {

        memberAdapter = MemberAdapter()

        binding.memberRecyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = memberAdapter
        }

    }

    private fun getBuilder() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val memberService = retrofit.create(MemberService::class.java)

        memberService.getMembers()
            .enqueue(object : Callback<MembersDto> {
            override fun onResponse(call: Call<MembersDto>, response: Response<MembersDto>) {
                // todo 예외처리
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


    companion object {
        private const val BASE_URL = "http://10.0.2.2:8080/"
    }
}