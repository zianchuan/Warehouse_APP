package com.example.warehouse_app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_shipment_history.*


class ShipmentHistoryActivity : AppCompatActivity() {


    private lateinit var database: FirebaseDatabase
   private lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipment_history)

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Shipment")

        getDataShipmentHistory()

        back_Icon.setOnClickListener(){
            val intent = Intent(this@ShipmentHistoryActivity, HomePage::class.java)
            startActivity(intent)
        }

    }

    private fun getDataShipmentHistory(){
        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.e("cancel", "Cancel");
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var list = ArrayList<ShipmentDatabaseModel>()
                for(data in snapshot.children){
                    val model = data.getValue(ShipmentDatabaseModel::class.java)
                    list.add(model as ShipmentDatabaseModel)
                }
                if(list.size > 0){
                    val adapter = ShipmentDataAdapter( list!!)
                    shipment_rv.adapter = adapter;
                }
            }
        })
    }
}