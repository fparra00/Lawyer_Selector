package com.example.lawyerselectorv2

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.LayoutAnimationController
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lawyer_user.*

class LawyerUser : AppCompatActivity() {
    //Aux Vars
    private lateinit var lyKnowledgeAnim: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lawyer_user)

        //Initialize Vars
        lyKnowledgeAnim = lyKnowledge

        //..onClicks..
        lyOnKnowledge.setOnClickListener {
            showLy(lyKnowledgeAnim, lyOnKnowledge, lawName4)
        }
        lyOnBio.setOnClickListener {
            showLy(lyBio, lyOnBio, lawBio)
        }
        lyOnStudies.setOnClickListener {
            showLy(lyStudies, lyOnStudies, lawStudies)
        }
    }


    public fun showLy(ly: LinearLayout, cly: LinearLayout, tv: TextView) {
        var set: AnimationSet = AnimationSet(true)
        var animation: Animation
        if (ly.visibility == View.GONE) {
            ly.visibility = View.VISIBLE
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up_icon, 0)
            cly.setBackgroundColor(resources.getColor(R.color.white))
            animation = TranslateAnimation(
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                -0.2f,
                Animation.RELATIVE_TO_SELF,
                0.0f
            )
        } else {
            ly.visibility = View.GONE
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_icon, 0)
            cly.setBackgroundColor(resources.getColor(R.color.bgGrey))
            animation = TranslateAnimation(
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f
            )
        }
        animation.duration = 300
        set.addAnimation(animation)
        var controller: LayoutAnimationController = LayoutAnimationController(set, 0.25f)
        ly.layoutAnimation = controller
        ly.startAnimation(animation)
    }


}