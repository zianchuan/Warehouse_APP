package com.example.warehouse_app

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_ship_product_.*
import kotlinx.android.synthetic.main.fragment_shipping_details.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ShippingDetailsFragment : Fragment() {
    val args: ShippingDetailsFragmentArgs by navArgs()
    private lateinit var  database:FirebaseDatabase
    private lateinit var  reference:DatabaseReference
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        //firebase
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Shipment")


    }

    //send data to shipment database
    private fun sendData(){

        var receiverName = editTextReceiverName.text.toString().trim()
        var contactNumber = editTextContactNumber.text.toString().trim()
        var address = editTextAddressr.text.toString().trim()
        var itemID = args.itemIDDetailsArgument.trim()

        if(receiverName.isNotEmpty() && contactNumber.isNotEmpty() && address.isNotEmpty()){
            var model = ShipmentDatabaseModel(receiverName, contactNumber, address, itemID)
            var id = reference.push().key

            //send data to firebase
            reference.child(id!!).setValue(model)
            //successful then go to other screen
            val action2 = ShippingDetailsFragmentDirections.actionShippingDetailsFragmentToSuccessfullyShipmentFragment()
            findNavController().navigate(action2)
        }
        else{
          Toast.makeText(activity, "All Fields are required!", Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_shipping_details, container, false)
    }

    private fun displayItemDetailImage(imageUrl: String, imageView: ImageView){
        try{
            val options: RequestOptions = RequestOptions()
            Glide.with(this).load(imageUrl).apply(options).into(imageView)
        }catch (e: Exception){
            Log.d("error", e.toString())
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShippingDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShippingDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemIDBack = args.itemIDDetailsArgument
        txt_ItemIDShippingDetails.text = itemIDBack.toString()
        displayItemDetailImage(args.imageUrlArgument, img_itemShippingdetails)

        back_Icon1.setOnClickListener{
            val action = ShippingDetailsFragmentDirections.actionShippingDetailsFragmentToNavItemDetailsShipment(itemIDBack)
            findNavController().navigate(action)
        }

        btn_ShipItem2.setOnClickListener{

            sendData()
        }
    }

}