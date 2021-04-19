package com.example.warehouse_app

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Gravity.START
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePage : AppCompatActivity() {
    @SuppressLint("WrongConstant")
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        //for navigation menu
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        menuIcon.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)

        }
        navigationView.itemIconTintList = null

        NavigationUI.setupWithNavController(navigationView, Navigation.findNavController(this, R.id.navHostFragment))
    }
    private fun logOut(){
        auth.signOut()
        val intent = Intent(this@HomePage, LoginActivity::class.java)
        startActivity(intent)
    }
}