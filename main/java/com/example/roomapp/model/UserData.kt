package com.example.roomapp.model

import androidx.annotation.Keep
import com.google.firebase.database.PropertyName
@Keep
data class UserData(
    @get:PropertyName("value")
    var value: List<User> = emptyList()
)
