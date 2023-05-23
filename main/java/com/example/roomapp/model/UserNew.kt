package com.example.roomapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.PropertyName
import kotlinx.android.parcel.Parcelize

 class UserNew {
     @get:PropertyName("id")
     val id: Int=0

     @get:PropertyName("firstName")
     val firstName: String=""

     @get:PropertyName("lastName")
     val lastName: String=""

     @get:PropertyName("age")
     val age: Int=0
 }