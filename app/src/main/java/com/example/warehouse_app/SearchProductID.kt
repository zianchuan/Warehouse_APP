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

class SearchProductID : AppCompatActivity() {
    private lateinit var productID: String

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_product_i_d)

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("Items")

        val btnSearchProductID = findViewById<TextView>(R.id.btnSearchProductID)
        btnSearchProductID.setOnClickListener {
            getProductID()
            getProductData(productID)
        }
    }

    private fun getProductID() {
        val et_ProductID = findViewById<EditText>(R.id.et_ProductID)
        productID = et_ProductID.text.toString()
    }

    private fun getProductData(productID: String) {
        reference.orderByChild("itemId").equalTo(productID).addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshot: DataSnapshot in snapshot.children) {
                    var databaseModel: DatabaseModel? = snapshot.getValue(DatabaseModel::class.java)
                    print(databaseModel)

                    val itemName = databaseModel?.itemName
                    val imageUrl = databaseModel?.image
                    val itemId = databaseModel?.itemId
                    val itemDescription = databaseModel?.itemDescription
                    val itemQuantity = databaseModel?.itemQuantity

                    val txtItemName = findViewById<TextView>(R.id.txtItemName)
                    val imageView = findViewById<ImageView>(R.id.imageView)
                    val txtItemID = findViewById<TextView>(R.id.txtItemId)
                    val txtItemDesc = findViewById<TextView>(R.id.txtItemDesc)
                    val txtItemQty = findViewById<TextView>(R.id.txtItemQty)

                    if (imageUrl != null) {
                        displayImage(imageUrl, imageView)
                    }

                    txtItemName.text = itemName
                    txtItemID.text = itemId
                    txtItemDesc.text = itemDescription
                    txtItemQty.text = itemQuantity
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