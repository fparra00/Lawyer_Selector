package com.example.lawyerselectorv2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.lawyerselectorv2.classes.LegalCase
import kotlinx.android.synthetic.main.loading_screen.*

class LoadingScreen : AppCompatActivity() {

    //Aux Var
    lateinit var legalCase: LegalCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_screen)

        if (intent.getSerializableExtra("casoLegal") != null) {
            legalCase = intent.getSerializableExtra("casoLegal") as LegalCase
        }
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
            val intent = Intent(this, LawyerUser::class.java)
            intent.putExtra("casoLegal", legalCase)
            startActivity(intent)
            finish()
        }, 3500)
    }
}