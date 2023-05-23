package com.example.roomapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomapp.network.MyApi
import com.example.roomapp.repository.LoginRemoteRepository
import com.example.roomapp.repository.LoginRepositoryImpl

class LoginViewModelFactory:ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepositoryImpl(
                    LoginRemoteRepository(MyApi.invoke())
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}