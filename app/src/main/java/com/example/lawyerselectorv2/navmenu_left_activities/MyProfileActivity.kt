package com.example.lawyerselectorv2.navmenu_left_activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.lawyerselectorv2.CreateLegalCaseActivity
import com.example.lawyerselectorv2.PrincipalMenuActivity
import com.example.lawyerselectorv2.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.activity_my_profile.drawerLayout
import kotlinx.android.synthetic.main.activity_my_profile.navView
import kotlinx.android.synthetic.main.activity_my_profile.topAppBar
import kotlinx.android.synthetic.main.activity_principal_menu.*

class MyProfileActivity : AppCompatActivity() {

    //Aux Vars
    lateinit var NavView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        //Init Vars
        topAppBar.menu.removeItem(R.id.iconFilterBot)
        NavView = navView
        //Aux Functions
        checkTopMenu()
        checkLeftMenu()
    }

    private fun checkTopMenu() {
        //Click on the Navigation Icon
        topAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        //Click on the other Icons of the Top Bar
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.iconHome -> {
                    goToHome()
                    true
                }
                else -> false
            }
        }
    }

    private fun checkLeftMenu() {
        NavView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuMyLawyers -> {
                    true
                }
                else -> false
            }
        }
    }

    private fun goToHome() {
        val intent: Intent = Intent(this, PrincipalMenuActivity::class.java)
        this.startActivity(intent)
        this.overridePendingTransition(
            R.anim.right_toleft_into_windows,
            R.anim.left_to_right_into_windows
        );
    }

}