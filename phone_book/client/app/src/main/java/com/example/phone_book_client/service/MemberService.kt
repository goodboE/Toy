package com.example.phone_book_client.service

import com.example.phone_book_client.MainActivity
import com.example.phone_book_client.entity.MemberDto
import com.example.phone_book_client.entity.MembersDto
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MemberService {

    @GET("members")
    fun getMembers(): Call<MembersDto>

    @POST("members")
    fun addMember(@Body memberDto: MemberDto): Call<MemberDto>

    @PUT("members/{id}")
    fun updateMember(
        @Path("id") id: Long,
        @Body memberDto: MemberDto
    ): Call<MemberDto>

    @DELETE("members/{id}")
    fun deleteMember(@Path("id") id: Long): Call<MembersDto>


}