package com.fishtudo.awesomeseries.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fishtudo.awesomeseries.R
import com.fishtudo.awesomeseries.repositories.PinRepository
import kotlinx.android.synthetic.main.activity_create_apin.*

class CreateAPinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_apin)
        configureButton()
    }

    private fun configureButton() {
        save_pin_button.setOnClickListener {
            PinRepository().savePin(this, pin.text.toString())
            Toast.makeText(this, R.string.saved_pin, Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}