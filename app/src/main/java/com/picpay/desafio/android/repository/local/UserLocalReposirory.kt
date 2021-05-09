package com.picpay.desafio.android.repository.local

import com.picpay.desafio.android.dao.UserDao
import com.picpay.desafio.android.model.data.User
import com.picpay.desafio.android.model.entity.UserEntity
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


interface UserLocalReposirory {
    fun saveUser(user: List<User>): Completable
    fun getLocalUsers(): Single<List<User>>

    class UserRepositoryImpl(private val userDao: UserDao) : UserLocalReposirory {

        override fun saveUser(user: List<User>) = userDao.saveUser(user.map {
            UserEntity(it.img, it.name, it.id, it.username)
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        override fun getLocalUsers() =
            userDao.loadUser().map {
                it.map { userEntity ->
                    User(
                        userEntity.img,
                        userEntity.name,
                        userEntity.id,
                        userEntity.username
                    )
                }
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}