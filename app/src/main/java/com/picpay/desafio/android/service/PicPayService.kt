package com.picpay.desafio.android.service



import com.picpay.desafio.android.model.dto.UserDto
import io.reactivex.Single
import retrofit2.http.GET


interface PicPayService {
    @GET("users")
    fun getUsers(): Single<List<UserDto>>
}