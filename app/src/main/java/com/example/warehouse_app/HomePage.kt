package com.example.warehouse_app

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Gravity.START
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePage : AppCompatActivity() {
    @SuppressLint("WrongConstant")

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        //for navigation menu
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        menuIcon.setOnClickListener{
            drawerLayout.openDrawer(START)
        }
        navigationView.itemIconTintList = null

        NavigationUI.setupWithNavController(navigationView, Navigation.findNavController(this, R.id.navHostFragment))
    }
}