package com.poc.ct.repository

import com.poc.ct.model.KanyeWestApiResponse
import com.poc.ct.model.Resource


interface MainRepository {
    suspend fun getQuotes(): Resource<KanyeWestApiResponse>
}