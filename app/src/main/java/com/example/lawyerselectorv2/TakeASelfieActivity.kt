package com.example.lawyerselectorv2

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_take_aselfie.*

class TakeASelfieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_aselfie)

        //....onClicks....
        lyClose.setOnClickListener {
            goToPrincipalMenu()
        }
    }


    /**
     * Show a Dialog if the user cancel the picture
     */
    private fun showDialog() {
        android.app.AlertDialog.Builder(this, R.style.CustomDialogTheme)
            .setTitle("You don't take a Picture?")
            .setMessage("Are you sure to want to exit, you can take the photo later")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, which ->
                })
            .show()
    }

    private fun goToPrincipalMenu() {
        val intent: Intent = Intent(this, PrincipalMenuActivity::class.java)
        this.startActivity(intent)
        this.overridePendingTransition(
            R.anim.right_toleft_into_windows,
            R.anim.left_to_right_into_windows
        );
    }
}