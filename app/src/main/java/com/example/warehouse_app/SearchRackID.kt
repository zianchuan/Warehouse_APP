package com.example.warehouse_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class SearchRackID : AppCompatActivity() {
    private lateinit var rackID : String

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_rack_i_d)

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("Rack")

        val btnSearchRackID= findViewById<TextView>(R.id.btnSearchRackID)
        btnSearchRackID.setOnClickListener{
            getRackID()
            getRackData(rackID)
        }
    }

    private fun getRackID(){
        val et_RackID = findViewById<EditText>(R.id.et_RackID)
        rackID = et_RackID.text.toString()
    }

    private fun getRackData(rackID: String){
        reference.orderByKey().addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot: DataSnapshot in snapshot.children) {
                    if (childSnapshot.hasChildren() && childSnapshot.hasChild(rackID)) {
                        var databaseModel : DatabaseModel? = childSnapshot.child(rackID).getValue(DatabaseModel::class.java)
                        print(databaseModel)

                        val itemName = databaseModel?.itemName
                        val imageUrl = databaseModel?.image
                        val itemId = databaseModel?.itemId
                        val itemDescription = databaseModel?.itemDescription
                        val itemQuantity = databaseModel?.itemQuantity
                        val rackId = rackID
                        val dateFormat = SimpleDateFormat("dd-MMM-yyyy hh:mm:ss", Locale.ENGLISH)
                        val putAwayDateTime = dateFormat.format(Date(databaseModel?.putAwayDateTime as Long))
                        // SimpleDateFormat

                        val txtItemName = findViewById<TextView>(R.id.txtItemName)
                        val imageView = findViewById<ImageView>(R.id.imageView)
                        val txtItemID = findViewById<TextView>(R.id.txtItemId)
                        val txtItemDesc = findViewById<TextView>(R.id.txtItemDesc)
                        val txtItemQty = findViewById<TextView>(R.id.txtItemQty)
                        val txtRackID = findViewById<TextView>(R.id.txtRackID)
                        val txtPutAwayDateTime = findViewById<TextView>(R.id.txtPutAwayDateTime)

                        if (imageUrl != null) {
                            displayImage(imageUrl, imageView)
                        }

                        txtItemName.text = itemName
                        txtItemID.text = itemId
                        txtItemDesc.text = itemDescription
                        txtItemQty.text = itemQuantity
                        txtRackID.text = rackId
                        txtPutAwayDateTime.text = putAwayDateTime
                    }
                }
            }
        })
    }

    private fun displayImage(imageUrl: String, imageView: ImageView) {
        try {
            val options: RequestOptions = RequestOptions()
                .centerCrop()

            Glide.with(this).load(imageUrl).apply(options).into(imageView)
        } catch (e: Exception) {
            Log.d("error", e.toString())
        }
    }
}