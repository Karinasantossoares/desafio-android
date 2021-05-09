package com.picpay.desafio.android.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.picpay.desafio.android.dao.UserDao
import com.picpay.desafio.android.model.entity.UserEntity

@Database(entities = [UserEntity::class],
    version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao() :UserDao

    companion object {
        fun instance(context: Context) =
            Room.databaseBuilder(context, AppDataBase::class.java, "AppDatabase")
                .fallbackToDestructiveMigration()
                .build()
    }
}