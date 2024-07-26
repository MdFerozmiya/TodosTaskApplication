package com.example.todostaskapplication.api

import com.example.todostaskapplication.models.CompletedStatus
import com.example.todostaskapplication.models.DeletedResponse
import com.example.todostaskapplication.models.GetTodosResponse
import com.example.todostaskapplication.models.RequestTodo
import com.example.todostaskapplication.models.Todos
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

private lateinit var webServices: WebServices


fun getHttpClient(): OkHttpClient {
    return  OkHttpClient.Builder()
        .build()
}

fun getApiService():WebServices{
    val apiServ = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://dummyjson.com/")
        .client(getHttpClient())
        .build()
    webServices = apiServ.create(WebServices::class.java)
    return webServices
}

interface WebServices {

    @GET("todos")
    suspend fun getAllTodos():GetTodosResponse

    @Headers("Content-Type: application/json")
    @POST("todos/add")
    suspend fun addTodoApi(
        @Body todo: RequestTodo
    ): Todos

    @Headers("Content-Type: application/json")
    @PUT("todos/{id}")
   suspend fun updateTodo(
        @Path("id") id: Int,
        @Body completedStatus: CompletedStatus
    ): Todos

   @DELETE("todos/{id}")
   suspend  fun deleteTodo(
        @Path("id") id: Int
    ): DeletedResponse

}

object Service {
    val retrofitService: WebServices by lazy {
        getApiService()
    }
}