package com.example.roomapp.fragments.login

import android.content.Intent
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomapp.R
import com.example.roomapp.data.RegisterDatabase
import com.example.roomapp.databinding.FragmentLoginBinding
import com.example.roomapp.model.Register
import com.example.roomapp.viewmodel.LoginViewModel
import com.example.roomapp.viewmodel.LoginViewModelFactory
import com.example.roomapp.viewmodel.RegisterViewModel
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import com.google.protobuf.Empty
import java.io.File
import java.util.concurrent.Executor



class LoginFragment : Fragment() {
//    R.layout.fragment_login
    private lateinit var registerViewModel: RegisterViewModel
  private lateinit var binding:FragmentLoginBinding
//            val binding: FragmentLoginBinding by lazy { FragmentLoginBinding.bind(requireView()) }

    lateinit var executor: Executor
    lateinit var biometricPrompt: androidx.biometric.BiometricPrompt
    lateinit var promptInfo: androidx.biometric.BiometricPrompt.PromptInfo
    private lateinit var loginViewModel:LoginViewModel



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = ViewModelProvider(
            this,
            LoginViewModelFactory()
        )
            .get(LoginViewModel::class.java)

        checkDeviceBiometric()
        binding.loginButton.setOnClickListener {
//            loginViewModel.showMessage
//           checkDetails()
            if(binding.userNameTextField.text.toString().isEmpty() or binding.passwordTextField.text.toString().isEmpty()){
                Toast.makeText(requireContext(), "Please enter required credentials!!", Toast.LENGTH_LONG).show()
            }
            else{
//                checkDeviceHasBiometric()
                checkDetails()
            }
            val logiDao = RegisterDatabase.getInstance(requireContext()).registerDao
            logiDao.getAllUsers()?.observe(viewLifecycleOwner){
                it?.let {
//                    Log.d("listUser",it.toString())
                    it.forEach {
                        Log.d("listUser",it.userName.toString())
                        Log.d("listpsw",it.passwrd.toString())
                    }
                }
            }

        }
//
        binding.SignUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
       setObserver()
    }

    private fun setObserver() {
      /*  loginViewModel.showMessage.observe(viewLifecycleOwner){
            it?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }*/
    }





    private fun checkDetails() {

        val userName = binding.userNameTextField.text.toString()
        val password = binding.passwordTextField.text.toString()
        val logiDao = RegisterDatabase.getInstance(requireContext()).registerDao
        logiDao.getUser(userName)?.observe(viewLifecycleOwner){
            it?.let {
                Log.d("RegUser",it.toString())
                it.forEach {
                    if (userName == it.userName && password== it.passwrd){
                        checkDeviceHasBiometric()
//                        Toast.makeText(requireContext(), "Valid credentials!!", Toast.LENGTH_LONG).show()
//                        findNavController().navigate(R.id.action_loginFragment_to_listFragment)
                    }
                    else{

                        Toast.makeText(requireContext(), "Please enter correct password!!", Toast.LENGTH_LONG).show()
                    }
                }
                Toast.makeText(requireContext(), "Please enter valid username or signup!!", Toast.LENGTH_LONG).show()
            }
        }
//        if(userName == "admin" && password== "admin"){
//            findNavController().navigate(R.id.action_loginFragment_to_listFragment)
//        }
//        else{
//            Toast.makeText(requireContext(), "Plz enter valid credentials!!", Toast.LENGTH_LONG).show()
//        }

    }


    fun checkDeviceHasBiometric(){

        executor = ContextCompat.getMainExecutor(requireContext())

        biometricPrompt = androidx.biometric.BiometricPrompt(this@LoginFragment,executor,object:androidx.biometric.BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(requireContext(),"Error",Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(requireContext(),"Successfully Failed",Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(requireContext(),"Authentication Success",Toast.LENGTH_SHORT).show()
//                checkDetails()
                findNavController().navigate(R.id.action_loginFragment_to_listFragment)
                loginViewModel.geoLocationRequest()
            }
        })
        promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login using fingerpint or face")
            .setNegativeButtonText("cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    fun checkDeviceBiometric() {
        val biometricManager = androidx.biometric.BiometricManager.from(requireContext())
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
//                info = "App can authenticate using biometrics."
                binding.loginButton.isEnabled = true

            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Log.e("MY_APP_TAG", "No biometric features available on this device.")
//                info = "No biometric features available on this device."
                binding.loginButton.isEnabled = false

            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
//                info = "Biometric features are currently unavailable."
                binding.loginButton.isEnabled = false

            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // Prompts the user to create credentials that your app accepts.
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
                }
                binding.loginButton.isEnabled  = false

                startActivityForResult(enrollIntent, 100)
            }
        }
//        binding.tvShowMsg.text = info
    }

}