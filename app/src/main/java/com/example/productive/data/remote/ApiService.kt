package com.example.productive.data.remote

import android.util.Log
import com.example.productive.data.local.entity.ExternalModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.DefaultRequest
import io.ktor.client.features.get
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.JsonSerializer
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

class ApiService {
    private val GET_ALL_TASKS_URL = "http://192.168.56.1:8080/tasks/all"
    private val POST_ADD_TASKS_URL = "http://192.168.56.1:8080/tasks/add"

    private val client = HttpClient(Android){
        install(DefaultRequest){
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
        install(JsonFeature){
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                isLenient = true
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }
        engine {
            connectTimeout = 100_00
            socketTimeout = 100_00
        }
    }

    suspend fun test(){
        val response : HttpResponse = client.get("https://ktor.io/")
        Log.e("Dhaval", "CLIENT : ${response.status} -- ${response.content}", )
    }

    suspend fun getAllTasksFromRemote() : List<ExternalModel> {
        return client.get<List<ExternalModel>> {
            url(GET_ALL_TASKS_URL)
        }
    }
    suspend fun postTasksToRemote(tasks : List<ExternalModel>){
        client.post<List<ExternalModel>>(urlString = POST_ADD_TASKS_URL){
            body = tasks
        }
    }
}