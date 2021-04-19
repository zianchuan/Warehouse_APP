package com.example.warehouse_app

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.*

class RackActivity : AppCompatActivity() {
    //Widgets
    var recyclerView: RecyclerView? = null

    //Firebase
    private var myRef: DatabaseReference? = null

    //variable
    private var rackList: ArrayList<Rack>? = null
    private var recyclerAdapter: RecyclerAdapter? = null
    private val mContext: Context? = null
    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rack)

        val btnText = intent.getStringExtra("RACK_ID")
        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        //Firebase
        myRef = FirebaseDatabase.getInstance().reference

        //ArrayList
        rackList = ArrayList()

        //Clear ArrayList
        ClearAll()

        //Get Data Method
        val query: Query = myRef!!.child("Rack").child(btnText.toString())
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                ClearAll()
                for (snapshot in dataSnapshot.children) {
                    val rack = Rack()
                    rack.imageUrl = snapshot.child("image").value.toString()
                    rack.itemName = snapshot.child("itemName").value.toString()
                    rack.itemId = snapshot.child("itemId").value.toString()
                    rack.itemQuantity = snapshot.child("itemQuantity").value.toString()
                    rack.rackId = snapshot.child("rackId").value.toString()
                    rackList!!.add(rack)
                }
                recyclerAdapter = RecyclerAdapter(applicationContext, rackList!!)
                recyclerView!!.adapter = recyclerAdapter
                recyclerAdapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    //private fun GetDataFromFirebase() {

    //}

    private fun ClearAll() {
        if (rackList != null) {
            rackList!!.clear()
            if (recyclerAdapter != null) {
                recyclerAdapter!!.notifyDataSetChanged()
            }
        }
        rackList = ArrayList()
    }




}