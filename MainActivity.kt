package com.example.facescanapp

import android.hardware.biometrics.BiometricManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import org.w3c.dom.Text
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    //ui views
    lateinit var btnAuth:Button
    lateinit var tvAuthStatus:TextView

    lateinit var executor: Executor
    lateinit var biometricPrompt: androidx.biometric.BiometricPrompt
    lateinit var promptInfo: androidx.biometric.BiometricPrompt.PromptInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAuth = findViewById(R.id.btnAuth)
        tvAuthStatus = findViewById(R.id.tvAuthstatus)

        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt =  androidx.biometric.BiometricPrompt(this@MainActivity,executor,object:BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                //if any error comes
                tvAuthStatus.text = "Error"+ errString
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                tvAuthStatus.text = "Successfully Failed"
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                tvAuthStatus.text = "Successfully Auth"
            }
        })

        //setup title,subtitle and desc on auth dialog

        promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login using fingerprint or face")
            .setNegativeButtonText("cancel")
            .build()

        //set event on button

        btnAuth.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }
}


