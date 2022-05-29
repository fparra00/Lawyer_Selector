package com.example.lawyerselectorv2

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_take_aselfie.*

class TakeASelfieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_aselfie)

        //....onClicks....
        lyClose.setOnClickListener {
            onAlertDialog()
        }
    }


    // When User cilcks on dialog button, call this method
    fun onAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Are you Sure to Exit?")
        builder.setMessage(
            "\n" +
                    "You can take the photo later"
        )
        builder.setPositiveButton(
            "EXIT"
        ) { dialog, id ->
            // User clicked Update Now button
        }
        builder.setNegativeButton(
            "STAY"
        ) { dialog, id ->
            // User cancelled the dialog
        }

        builder.show()
    }
}