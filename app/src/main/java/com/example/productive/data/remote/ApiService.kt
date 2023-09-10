package com.example.productive.data.remote

import androidx.lifecycle.LiveData
import com.example.productive.data.local.entity.ExternalModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("tasks/all")
    suspend fun getAllTasks() : List<ExternalModel>

    @POST("tasks/add")
    suspend fun postTasks(@Body body : List<ExternalModel>)
}

object RetrofitHelper{
    val BASE_URL = "https://productive-backend.onrender.com/"
    fun getApiService() : ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}