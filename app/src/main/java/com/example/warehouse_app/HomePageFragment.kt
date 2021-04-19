package com.example.warehouse_app

import android.content.Intent
import android.net.wifi.hotspot2.pps.HomeSp
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home_page.*
import kotlinx.android.synthetic.main.fragment_home_page.img_userprofile
import kotlinx.android.synthetic.main.fragment_home_page.txt_staffPosition

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomePageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var auth: FirebaseAuth
    var databaseReference :  DatabaseReference? = null
    var database: FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")
    }
//navigate to another page
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadProfilePicAndName();
        report_icon.setOnClickListener{
            val action = HomePageFragmentDirections.actionNavHomepageToNavMyProfile2()
           findNavController().navigate(action)
        }

        menuPackShipment.setOnClickListener{
           val action2 = HomePageFragmentDirections.actionNavHomepageToShipmentFragment()
            findNavController().navigate(action2)
        }
    }

    private fun loadProfilePicAndName(){
        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        userreference?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val imageUrl = snapshot.child("image").value.toString()

                if(img_userprofile == null){
                    return
                }
                else if(img_userprofile!=null && imageUrl != null) {
                    displayImage(imageUrl, img_userprofile!!)
                }

                txt_staffName.text = snapshot.child("firstname").value.toString()+" " +snapshot.child("lastname").value.toString()
                txt_staffPosition.text = snapshot.child("position").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun displayImage(imageUrl: String, profileImage: ImageView) {
        try {
            val options: RequestOptions = RequestOptions()
                .centerCrop()

            Glide.with(this).load(imageUrl).apply(options).into(profileImage)
        } catch (e: Exception) {
            Log.d("error", e.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomePageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}