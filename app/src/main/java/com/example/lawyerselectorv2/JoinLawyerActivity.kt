package com.example.lawyerselectorv2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_join_lawyer.*

class JoinLawyerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_lawyer)
        // .... onClicks ....
        btnNext.setOnClickListener {
            goToSignUp2()
        }
    }

    //.... goTo ....
    private fun goToSignUp2() {
        val intent = Intent(this, SignupActivity_2::class.java)
        startActivity(intent)
    }
}