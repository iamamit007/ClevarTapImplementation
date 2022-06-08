package com.poc.ct.model.api

import com.poc.ct.model.KanyeWestApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface KanyeWestApi {
    @GET(".")
    suspend fun getQuotes():Response<KanyeWestApiResponse>
}