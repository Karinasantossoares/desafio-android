package com.picpay.desafio.android.retrofit


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun initRetrofit() :Retrofit{
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = (HttpLoggingInterceptor.Level.BODY)
    val client: OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(interceptor)
        .callTimeout(1, TimeUnit.MINUTES)
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .build()
    return Retrofit.Builder()
        .client(client)
        .baseUrl( "http://careers.picpay.com/tests/mobdev/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()


}