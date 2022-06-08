package com.poc.ct.repository

import com.poc.ct.model.api.KanyeWestApi
import com.poc.ct.model.KanyeWestApiResponse
import com.poc.ct.model.Resource
import java.lang.Exception
import javax.inject.Inject


class DefualtMainRepository @Inject constructor(
    val kanyeWestApi: KanyeWestApi
):MainRepository {
    override suspend fun getQuotes(): Resource<KanyeWestApiResponse> {
        return try {
            val response = kanyeWestApi.getQuotes()
            val result = response.body()
            if (response.isSuccessful && result != null){
                Resource.Success(result)
            }else{
                Resource.Error("An Error occurred")
            }
        }catch (e:Exception){
            println("kanyeWestApi $e")
            Resource.Error("An $e occurred")
        }
    }
}