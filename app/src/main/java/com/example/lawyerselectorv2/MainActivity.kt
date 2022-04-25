package com.example.lawyerselectorv2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogIn.setOnClickListener {
            goToLoginView()
        }
    }

    fun goToLoginView(){
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }

    fun goToRegisterView(){

    }
}