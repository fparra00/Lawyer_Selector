package com.example.lawyerselectorv2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash_screen)

        //Aux Functions
        enableAnimator()
    }

    private fun enableAnimator() {
        var anim: Animation = AnimationUtils.loadAnimation(this, R.anim.desplaz_up)
        var anim2: Animation = AnimationUtils.loadAnimation(this, R.anim.desplaz_down)

        txtLawyer.animation = anim2
        txtSelector.animation = anim2
        pBar.animation = anim2
        imLogo.animation = anim

        Handler(Looper.getMainLooper()).postDelayed({
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, 3500)
    }
}