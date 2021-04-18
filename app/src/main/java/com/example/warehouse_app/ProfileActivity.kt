package com.example.warehouse_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference :  DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        loadProfile()
    }
    private fun loadProfile() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        emailText.text = "Email  -- > "+user?.email

        userreference?.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {


                val imageUrl = snapshot.child("image").value.toString()

                val profileImage = findViewById<ImageView>(R.id.profileImage)

                if(imageUrl != null){
                    displayImage(imageUrl, profileImage)
                }

                firstnameText.text = "Firstname ---> "+snapshot.child("firstname").value.toString()
                lastnameText.text = "Last name ---> "+snapshot.child("lastname").value.toString()
                birthdayText.text = "Birthday ---> "+snapshot.child("birthday").value.toString()
                positionText.text = "Position ---> "+snapshot.child("position").value.toString()
                employedDateText.text = "Employed Date ---> "+snapshot.child("employedDate").value.toString()
                staffIDText.text = "Staff ID ---> "+snapshot.child("staffID").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
            finish()
        }
    }
    private fun displayImage(imageUrl: String, profileImage: ImageView) {
        try {
            val options: RequestOptions = RequestOptions()
                .centerCrop()

            Glide.with(this).load(imageUrl).apply(options).into(profileImage)
        } catch (e: Exception) {
            Log.d("error", e.toString())
        }
    }

}