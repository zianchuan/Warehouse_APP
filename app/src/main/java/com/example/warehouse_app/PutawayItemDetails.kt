package com.example.warehouse_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class PutawayItemDetails : AppCompatActivity() {
    companion object {
        const val SCAN_RESULT_RACK = "SCAN_RESULT_RACK"
        const val REFERENCE_ID = "REFERENCE_ID"
    }

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_putaway_item_details)

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Items")
        var bundle = intent.extras
        if (bundle != null) {
            val scanResultRack: String = bundle.get(SCAN_RESULT_RACK).toString()
            val referenceId: String = bundle.get(REFERENCE_ID).toString()
            sendData(scanResultRack, referenceId)
            getData(referenceId)
        }

        val btnReviewAnotherProduct = findViewById<Button>(R.id.btnReviewAnotherProduct)
        btnReviewAnotherProduct.setOnClickListener {
            val intent = Intent(this, ScanItem::class.java)
            startActivity(intent)
        }
    }

    private fun sendData(scanResultRack: String, referenceId: String) {
        if (scanResultRack.isNotEmpty() && referenceId.isNotEmpty()) {
            val txtRackID = findViewById<TextView>(R.id.txtRackID)
            txtRackID.text = scanResultRack
            //send data to database
            reference.child(referenceId!!).child("rackId").setValue(scanResultRack)
            reference.child(referenceId!!).child("putAwayDateTime").setValue(Date().time)
        } else {
            Toast.makeText(applicationContext, "All fields required", Toast.LENGTH_LONG).show()
        }
    }

    private fun getData(referenceId: String) {
        reference.child(referenceId).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var databaseModel : DatabaseModel? = snapshot.getValue(DatabaseModel::class.java)
                print(databaseModel)

                val itemName = databaseModel?.itemName
                val imageUrl = databaseModel?.image
                val itemId = databaseModel?.itemId
                val itemDescription = databaseModel?.itemDescription
                val itemQuantity = databaseModel?.itemQuantity
                val rackID = databaseModel?.rackId
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
                txtRackID.text = rackID
                txtPutAwayDateTime.text = putAwayDateTime

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
