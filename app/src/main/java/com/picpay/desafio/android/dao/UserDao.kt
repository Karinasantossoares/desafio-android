package com.picpay.desafio.android.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.picpay.desafio.android.model.entity.UserEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(userEntity: List<UserEntity>): Completable

    @Query("SELECT * FROM users")
    fun loadUser(): Single<List<UserEntity>>
}