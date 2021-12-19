package com.fishtudo.awesomeseries.biometry

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

class BiometryManager {

    fun promptBiometryLogin(
        activity: FragmentActivity,
        callback: BiometricPrompt.AuthenticationCallback
    ) {
        val biometricPrompt = BiometricPrompt(
            activity,
            ContextCompat.getMainExecutor(activity),
            callback
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()
        biometricPrompt.authenticate(promptInfo)
    }


    fun appCanAuthenticateWithBiometry(context: Context): Boolean =
        deviceHasBiometryEnabled(context) == BiometricManager.BIOMETRIC_SUCCESS

    private fun deviceHasBiometryEnabled(context: Context): Int =
        BiometricManager.from(context).canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
}