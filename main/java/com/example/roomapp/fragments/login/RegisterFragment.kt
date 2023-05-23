package com.example.roomapp.fragments.login

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomapp.R
import com.example.roomapp.databinding.FragmentRegisterBinding
import com.example.roomapp.model.Register
import com.example.roomapp.viewmodel.RegisterViewModel

class RegisterFragment : Fragment() {

    private lateinit var mRegisterViewModel: RegisterViewModel
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        mRegisterViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        binding.registerButton.setOnClickListener {
            insertToRegisterDatabase()
        }

        return binding.root
    }

    private fun insertToRegisterDatabase() {
        val rFirstName = binding.firstNameTextField.text.toString()
        val rLastName  = binding.secondNameTextField.text.toString()
        val rUserName = binding.userNameTextField.text.toString()
        val rPassword = binding.passwordTextField.text.toString()

        if(inputCheck(rFirstName,rLastName, rUserName, rPassword)){
            val reister = Register(0,rFirstName,rLastName,rUserName,rPassword)
            mRegisterViewModel.addLogger(reister)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun inputCheck(rFirstName: String, rLastName: String, rUserName: String, rPassword:String): Boolean{
        return !(TextUtils.isEmpty(rFirstName) && TextUtils.isEmpty(rLastName) && TextUtils.isEmpty(rUserName) && TextUtils.isEmpty(rPassword))
    }
}