package com.example.roomapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.roomapp.model.Register
import kotlinx.coroutines.flow.Flow

@Dao
interface RegisterDao {
    @Insert
    suspend fun insert(register: Register)

    @Query("SELECT * FROM Register_users_table ORDER BY userId DESC")
    fun getAllUsers():LiveData<List<Register>>


    @Query("SELECT * FROM Register_users_table WHERE user_name = :userName")
    fun getUser(userName:String):LiveData<List<Register>>

//    @Query("SELECT * FROM Register_users_table WHERE user_name LIKE :userName")
//    fun getUsername(userName:String):LiveData<Register>

//    @Query("SELECT userId||','||firstName||','||lastName||','||userName||','||passwrd FROM postDataLocal")
//    fun getLocalCsv():List<String>

}