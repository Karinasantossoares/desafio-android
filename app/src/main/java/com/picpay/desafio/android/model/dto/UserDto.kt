package com.picpay.desafio.android.model.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.picpay.desafio.android.model.data.User
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class UserDto(
    @SerializedName("img") val img: String,
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String
) : Parcelable {
    fun toUser(): User =
        User(
            img,
            name,
            id,
            username
        )
}