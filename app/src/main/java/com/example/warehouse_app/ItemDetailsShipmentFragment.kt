package com.example.warehouse_app

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_item_details_shipment.*
import kotlinx.android.synthetic.main.fragment_shipment.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_ship_product_.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ItemDetailsShipmentFragment : Fragment() {
    val args: ItemDetailsShipmentFragmentArgs by navArgs()
    private var imageUrlData :String? = null
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Items")
        getData()
    }

    private fun getData(){
        reference.orderByChild("itemId").equalTo(args.itemIDArgument).addValueEventListener(object:ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.e("cancel", error.toString())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    var model = ds.getValue(ItemsDatabaseModel::class.java)
                    print(model)
                    txtItemName.text = model?.itemName
                    txt_ItemIDBottom.text = model?.itemId
                    txt_desc.text = model?.itemDescription
                    txt_rackID.text = model?.rackId
                    val imageUrlItemDetails = model?.image
                    imageUrlData = model?.image

                    if(imageUrlItemDetails!=null){
                        displayItemDetailImage(imageUrlItemDetails, img_itemdetails)
                    }
                }
            }
        })
    }

    private fun displayItemDetailImage(imageUrl: String, imageView: ImageView){
        try{
            val options: RequestOptions = RequestOptions()
            Glide.with(this).load(imageUrl).apply(options).into(imageView)
        }catch (e: Exception){
            Log.d("error", e.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       return inflater.inflate(R.layout.fragment_item_details_shipment, container, false)

    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemDetailsShipmentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemID = args.itemIDArgument
        txt_ItemID.text = itemID.toString()


        back_Icon.setOnClickListener {
        val action = ItemDetailsShipmentFragmentDirections.actionNavItemDetailsShipmentToNavShipProduct()
        findNavController().navigate(action)
        }

        btn_ShipItem.setOnClickListener{
            val id = args.itemIDArgument
            val imageUrl  = imageUrlData.toString()
            val bundle = bundleOf("id" to id)
            val bundle2 = bundleOf("imageUrl" to imageUrl)
            val action2 = ItemDetailsShipmentFragmentDirections.actionNavItemDetailsShipmentToShippingDetailsFragment(id, imageUrl)
            findNavController().navigate(action2)

        }

    }
}