package com.example.warehouse_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_rack_i_d_report.*
import kotlinx.android.synthetic.main.activity_shipment_history.*

class RackIDReportActivity : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rack_i_d_report)

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Items")

        getReport()
    }

    private fun getReport(){
        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.e("cancel", "Cancel");
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var list = ArrayList<ItemsDatabaseModel>()
                for(data in snapshot.children){
                    val model = data.getValue(ItemsDatabaseModel::class.java)
                    list.add(model as ItemsDatabaseModel)
                }
                if(list.size > 0){
                    val adapter = ReportDataAdapter( list!!)
                    report_rv.adapter = adapter;
                }
            }
        })
    }


}