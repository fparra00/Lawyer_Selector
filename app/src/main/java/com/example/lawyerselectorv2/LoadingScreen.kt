package com.example.lawyerselectorv2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.loading_screen.*

class LoadingScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_screen)
        //Aux Functions
        enableAnimator()
    }


    /**
     * Enable the Animation in Splash Screen
     */
    private fun enableAnimator() {
        var anim: Animation = AnimationUtils.loadAnimation(this, R.anim.left_to_right)
        lyLoadingScreen.animation = anim

        Handler(Looper.getMainLooper()).postDelayed({
            val mainIntent = Intent(this, LawyerUser::class.java)
            startActivity(mainIntent)
            finish()
        }, 3500)
    }
}