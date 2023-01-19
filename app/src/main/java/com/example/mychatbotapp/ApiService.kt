package com.example.mychatbotapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

private const val CONTENT_TYPE = "Accept:application/json"

interface ApiService {
    @Headers(CONTENT_TYPE)
    @GET
    fun getMessage(@Url url: String): Call<BotResponseModel>
}