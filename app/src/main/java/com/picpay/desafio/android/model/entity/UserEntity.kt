package com.picpay.desafio.android.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    val img: String,
    val name: String,
    @PrimaryKey(autoGenerate = true)val id: Int =0,
    val username: String)
