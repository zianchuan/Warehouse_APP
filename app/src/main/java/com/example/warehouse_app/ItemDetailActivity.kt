package com.example.warehouse_app

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.analytics.FirebaseAnalytics.Param.ITEM_ID
import com.google.firebase.database.*

class ItemDetailActivity : AppCompatActivity() {
    //var itemID: TextView? = null
    //var itemNAME: TextView? = null
    //var itemDESC: TextView? = null
    //var itemQUANTITY: TextView? = null
    //var rackID: TextView? = null
    //var itemPic: ImageView? = null
    //var reff: DatabaseReference? = null
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        //val itemIdIntent = intent.getStringExtra("itemID")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        val bundle = intent.extras
        if (bundle != null) {
            val itemId: String = bundle.get(ITEM_ID).toString()

            database = FirebaseDatabase.getInstance();
            reference = database.getReference("Items")

            reference.orderByChild("itemId").equalTo(itemId).addValueEventListener(object :ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for(snapshot in dataSnapshot.children) {
                        var model = snapshot.getValue(ItemsDatabaseModel::class.java)
                        print(model)
                        //val itemId = dataSnapshot.child("itemId").value.toString()

                        val txtItemId = findViewById<View>(R.id.itemId) as TextView
                        val txtItemName = findViewById<View>(R.id.itemName) as TextView
                        val txtItemDesc = findViewById<View>(R.id.itemDesc) as TextView
                        val txtItemQuantity = findViewById<View>(R.id.itemQuantity) as TextView
                        val txtRackId = findViewById<View>(R.id.rackId) as TextView
                        val imgItemPic = findViewById<View>(R.id.itemPic) as ImageView

                        txtItemId.text = itemId
                        txtItemName.text = model?.itemName
                        txtItemDesc.text = model?.itemDescription
                        txtItemQuantity.text = model?.itemQuantity
                        txtRackId.text = model?.rackId

                        val imageUrl = snapshot.child("image").value.toString()

                        //Glide.with(this@ItemDetailActivity.applicationContext).load(imageUrl).into(imgItemPic)

                        if(imageUrl != null){
                            try{
                                val options: RequestOptions = RequestOptions()
                                Glide.with(this@ItemDetailActivity).load(imageUrl).apply(options).into(imgItemPic)
                            }catch (e: Exception){
                                Log.d("error", e.toString())
                            }
                        }

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("cancel", error.toString())
                }
            });
        }

    }




    companion object {
        const val ITEM_ID = "ITEM_ID"
    }
}

