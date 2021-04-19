package com.example.warehouse_app

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.*

class RecyclerAdapter(
    private val mContext: Context,
    private val rackList: ArrayList<Rack>
) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        //Text View
        holder.itemName.text = rackList[position].itemName
        holder.itemId.text = rackList[position].itemId
        holder.itemQuantity.text = rackList[position].itemQuantity
        holder.rackId.text = rackList[position].rackId
	    val itemID = holder.itemId.text
        holder.btnViewItem.setOnClickListener { v -> //start intent
            val intent = Intent(v.context, ItemDetailActivity::class.java)
            intent.putExtra(ItemDetailActivity.ITEM_ID, itemID)
            v.context.startActivity(intent)
        }
        //Image View : Glider
        //
        Glide.with(mContext).load(rackList[position].imageUrl).into(holder.itemPic)
    }

    override fun getItemCount(): Int {
        return rackList.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        //Widget
        var itemPic: ImageView = itemView.findViewById(R.id.itemPic)
        var itemName: TextView = itemView.findViewById(R.id.itemName)
        var itemId: TextView = itemView.findViewById(R.id.itemId)
        var itemQuantity: TextView = itemView.findViewById(R.id.itemQuantity)
        var rackId: TextView = itemView.findViewById(R.id.rackId)
        var btnViewItem: Button = itemView.findViewById(R.id.viewItemButton)
    }

    companion object {
        private const val Tag = "RecyclerView"
    }

}