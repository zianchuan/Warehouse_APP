package com.example.warehouse_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.R
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.shipmentrecyclerview.view.*

class ShipmentDataAdapter(var list:ArrayList<ShipmentDatabaseModel>) :RecyclerView.Adapter<ShipmentDataAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var receivername = itemView.tv_receiverName
        var contactnumber = itemView.tv_contact_number
        var address = itemView.tv_address
        var itemid = itemView.tv_itemid
        var time = itemView.tv_shipmentdatetime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(LayoutInflater.from(parent.context).inflate(com.example.warehouse_app.R.layout.shipmentrecyclerview, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.receivername.text = list[position].receiverName
        holder.contactnumber.text = list[position].contactNumber
        holder.address.text = list[position].address
        holder.itemid.text = list[position].itemId
        holder.time.text = list[position].shipmentDateTime
    }

}