package com.fishtudo.awesomeseries.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fishtudo.awesomeseries.R
import com.fishtudo.awesomeseries.model.Session
import com.fishtudo.awesomeseries.repositories.PinRepository
import kotlinx.android.synthetic.main.activity_unlock.*

class UnlockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unlock)
        configureButton()
    }

    private fun configureButton() {
        login.setOnClickListener {
            handleLoginButtonClick()
        }
    }

    private fun handleLoginButtonClick() {
        if (PinRepository().readPin(this) == pin_typed.text.toString()) {
            startLoginActivity()
        } else {
            showErrorMessage()
        }
    }

    private fun showErrorMessage() {
        Toast.makeText(this, R.string.pin_invalid, Toast.LENGTH_SHORT).show()
    }

    private fun startLoginActivity() {
        val intent = Intent(this, MainActivity::class.java)
        Session.onLoginSucceed()
        startActivity(intent)
        finish()
    }
}