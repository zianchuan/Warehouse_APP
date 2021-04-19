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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.net.URL


class ReceiveItem : AppCompatActivity() {

    companion object {
        const val SCAN_RESULT = "SCAN_RESULT"
    }

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    private lateinit var referenceId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_item)

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("Items")
        val bundle = intent.extras
        if (bundle != null) {
            val scanResult: String = bundle.get(SCAN_RESULT).toString()
            sendData(scanResult)
        }

        val btnPutAway = findViewById<Button>(R.id.btnPutAway)
        btnPutAway.setOnClickListener {
            val intent = Intent(this, PutawayScan::class.java)
            intent.putExtra(PutawayScan.REFERENCEID, referenceId)
            startActivity(intent)

        }
    }

    private fun sendData(scanResult: String) {

        if (scanResult.isNotEmpty() && scanResult.contains("|")) {
            val scanResultArray = scanResult.split("|")
            val itemName = scanResultArray[0]
            val imageUrl = scanResultArray[1]
            val itemId = scanResultArray[2]
            val itemDescription = scanResultArray[3]
            val itemQuantity = scanResultArray[4]

            val txtItemName = findViewById<TextView>(R.id.txtItemName)
            val imageView = findViewById<ImageView>(R.id.imageView)
            val txtItemID = findViewById<TextView>(R.id.txtItemId)
            val txtItemDesc = findViewById<TextView>(R.id.txtItemDesc)
            val txtItemQty = findViewById<TextView>(R.id.txtItemQty)

            displayImage(imageUrl, imageView)

            txtItemName.text = itemName
            txtItemID.text = itemId
            txtItemDesc.text = itemDescription
            txtItemQty.text = itemQuantity

            val model = DatabaseModel(itemName, itemId, imageUrl, itemDescription, itemQuantity)

            referenceId = reference.push().key.toString()

            //send data to database (update)
            reference.child(referenceId!!).setValue(model)
        } else {
            Toast.makeText(applicationContext, "All fields required", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayImage(imageUrl: String, imageView: ImageView) {
        try {
            val options: RequestOptions = RequestOptions()
                    .centerCrop()

            Glide.with(this).load(imageUrl).apply(options).into(imageView)
        } catch (e: Exception) {
            Log.d("o0o", e.toString())
        }
    }
}

