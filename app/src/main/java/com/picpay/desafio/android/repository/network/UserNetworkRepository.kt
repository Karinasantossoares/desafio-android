package com.picpay.desafio.android.repository.network

import com.picpay.desafio.android.model.data.User
import com.picpay.desafio.android.service.PicPayService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface UserNetworkRepository {
    fun getUsers(): Single<List<User>>

    class UserNetworkRepositoryImpl(private val service: PicPayService) : UserNetworkRepository {
        override fun getUsers() = service.getUsers().map {
            it.map { userDao -> userDao.toUser() }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

}