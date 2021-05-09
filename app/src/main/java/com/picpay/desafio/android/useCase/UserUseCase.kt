package com.picpay.desafio.android.useCase

import com.picpay.desafio.android.model.data.User
import com.picpay.desafio.android.repository.local.UserLocalReposirory
import com.picpay.desafio.android.repository.network.UserNetworkRepository
import io.reactivex.Completable
import io.reactivex.Single

interface UserUseCase {
    fun getUsers(): Single<List<User>>
    fun loadUserLocal() :Single<List<User>>
    fun saveUserLocal(user:List<User>):Completable

    class UserUseCaseImpl(
        private val networkRepository: UserNetworkRepository,
        private val userLocalReposirory: UserLocalReposirory
    ) : UserUseCase {

        override fun getUsers() = networkRepository.getUsers()
        override fun loadUserLocal() = userLocalReposirory.loadUser()
        override fun saveUserLocal(user:List<User>)= userLocalReposirory.saveUser(user)

    }
}