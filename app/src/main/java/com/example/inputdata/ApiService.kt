package com.example.inputdata

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

val  BASE_URL = "https://jsonplaceholder.typicode.com/"
interface ApiService {
//    @GET("todos")
//    fun getTodoss(): Call<List<Todos>>
//
//    @GET("todos/{no}")
//    fun getTodos(@Path("no") no:Int): Call<Todos>

    @GET("todos/")
    fun getFromUserId(@QueryMap map: HashMap<String,String>): Call<List<Todos>>

    companion object{
        operator fun invoke(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}