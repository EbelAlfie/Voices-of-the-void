package com.app.customerservice.di

import com.app.customerservice.data.service.ApiService
import com.app.customerservice.presentation.App
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HttpClient {

  private val okHttpClient: OkHttpClient by lazy {
    val interceptor = HttpLoggingInterceptor().apply {
      this.setLevel(BODY)
    }

    OkHttpClient
      .Builder()
      .addInterceptor(interceptor)
      .build()
  }

  private val retrofit: Retrofit by lazy {
    Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl("http://10.42.0.1:5678/")
      .addConverterFactory(GsonConverterFactory.create(Gson()))
      .build()
  }

  fun provideWebSocket(socketListener: WebSocketListener): WebSocket {
    val request = Request.Builder()
      .url("ws://10.42.0.1:3001/ws?uid=${App.User.id}")
      .build()
    return okHttpClient.newWebSocket(request, socketListener)
  }

  fun provideApiService(): ApiService {
    return retrofit.create(ApiService::class.java)
  }
}