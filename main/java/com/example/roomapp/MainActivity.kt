package com.example.roomapp


import android.content.Intent
import android.hardware.biometrics.BiometricManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.roomapp.databinding.ActivityMainBinding
import dagger.hilt.android.HiltAndroidApp

//@HiltAndroidApp
class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val appBarConfiguration = AppBarConfiguration.Builder(R.id.listFragment,R.id.loginFragment).build()

        setupActionBarWithNavController(findNavController(R.id.fragment),appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.fragment)
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()

    }

    override fun onBackPressed() {
        val exitIntent = Intent(Intent.ACTION_MAIN)
        exitIntent.addCategory(Intent.CATEGORY_HOME)
        exitIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(exitIntent)

    }


}
