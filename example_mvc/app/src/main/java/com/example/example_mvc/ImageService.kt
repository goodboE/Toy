package com.example.example_mvc

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ImageService {

    @Headers("Authorization: Client-ID 1MXzjGUkPllsUcwZm9hgq8dMGzLIdFk0AN_8X296ZWw")
    @GET("photos/random")
    fun getRandomImage() : Call<ImageResponse>

}