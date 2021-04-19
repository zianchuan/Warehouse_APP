package com.example.warehouse_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.R
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.reportrecyclerview.view.*
import kotlinx.android.synthetic.main.shipmentrecyclerview.view.*

class ReportDataAdapter(var list:ArrayList<ItemsDatabaseModel>) :RecyclerView.Adapter<ReportDataAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var rackid = itemView.tv_rackid
        var name = itemView.tv_itemname
        var quantity = itemView.tv_quantity
        var time = itemView.tv_time
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(com.example.warehouse_app.R.layout.reportrecyclerview, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.rackid.text = list[position].rackId
        holder.name.text = list[position].itemName
        holder.quantity.text = list[position].itemQuantity
        holder.time.text = list[position].putAwayDateTime.toString()
    }

}