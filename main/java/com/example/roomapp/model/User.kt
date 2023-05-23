package com.example.roomapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.PropertyName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    @get:PropertyName("id")
    val id: Int,
    @get:PropertyName("firstName")
    val firstName: String,
    @get:PropertyName("lastName")
    val lastName: String,
    @get:PropertyName("age")
    val age: Int
):Parcelable
{
    constructor(): this(0,"", "",0)

}