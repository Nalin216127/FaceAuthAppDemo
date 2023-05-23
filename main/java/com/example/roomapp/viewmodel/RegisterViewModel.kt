package com.example.roomapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.roomapp.data.RegisterDatabase
import com.example.roomapp.data.UserDatabase
import com.example.roomapp.databinding.FragmentLoginBinding
import com.example.roomapp.model.Register
import com.example.roomapp.network.MyApi

import com.example.roomapp.repository.RegisterRepository
import com.example.roomapp.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit


class RegisterViewModel(application: Application):AndroidViewModel(application){
    val logAll:LiveData<List<Register>>

    private val repository:RegisterRepository
    lateinit var binding: FragmentLoginBinding


    init {
           val registerDao = RegisterDatabase.getInstance(application).registerDao
           repository = RegisterRepository(registerDao)
           logAll = repository.logAll

    }

    fun addLogger(register: Register){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(register)

        }
    }










}