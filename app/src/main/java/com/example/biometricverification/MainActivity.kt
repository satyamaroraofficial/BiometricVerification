package com.example.biometricverification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activity = this
        val executor = Executors.newSingleThreadExecutor()
        val biometricPrompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if(errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    //User clicked negative button
                } else {
                    //TODO
                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                runOnUiThread {
                    this@MainActivity.startActivity(Intent(activity, Main2Activity::class.java))
                }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                runOnUiThread{
                    Toast.makeText(activity, "Authentication failed, PLease try again...", Toast.LENGTH_SHORT).show()
                }
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Authentication prompt!")
            .setSubtitle("Set the subtitle to display")
            .setDescription("Verification required")
            .setNegativeButtonText("Cancel")
            .build()

        authenticateButton.setOnClickListener{
            biometricPrompt.authenticate(promptInfo)
        }
    }
}
