package com.example.lawyerselectorv2.navmenu_left_activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.lawyerselectorv2.PrincipalMenuActivity
import com.example.lawyerselectorv2.R
import com.example.lawyerselectorv2.SplashScreenActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_my_profile.*

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
                R.id.menuProfile -> {
                    goToMyProfile()
                    true
                }
                R.id.menuCases -> {
                    goToMyLegalCases()
                    true
                }
                R.id.menuLogOut -> {
                    logOut()
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

    private fun goToMyProfile() {
        val intent: Intent = Intent(this, MyProfileActivity::class.java)
        this.startActivity(intent)
        this.overridePendingTransition(
            R.anim.right_toleft_into_windows,
            R.anim.left_to_right_into_windows
        );
    }

    private fun goToMyLawyers() {
        val intent: Intent = Intent(this, MyProfileActivity::class.java)
        this.startActivity(intent)
        this.overridePendingTransition(
            R.anim.right_toleft_into_windows,
            R.anim.left_to_right_into_windows
        );
    }

    private fun goToMyLegalCases() {
        val intent: Intent = Intent(this, MyLegalCasesActivity::class.java)
        this.startActivity(intent)
        this.overridePendingTransition(
            R.anim.right_toleft_into_windows,
            R.anim.left_to_right_into_windows
        );
    }

    private fun logOut() {
        val intent: Intent = Intent(this, SplashScreenActivity::class.java)
        this.startActivity(intent)
    }

}