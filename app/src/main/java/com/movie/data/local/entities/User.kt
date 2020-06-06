package com.movie.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID = 0

@Entity
data class User(
    val lid: Int? = null,
    val rid: Int? = null,
    val user_email: String? = null,
    val user_name: String? = null
) {
    @PrimaryKey
    var uid: Int = CURRENT_USER_ID
}