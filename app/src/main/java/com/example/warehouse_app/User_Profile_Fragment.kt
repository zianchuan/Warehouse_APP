package com.example.warehouse_app

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.contentValuesOf
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_user__profile_.*
import java.util.*
import android.content.Intent as Intent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseUser as FirebaseUser1

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [User_Profile_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class User_Profile_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("profile")

    }

    private fun loadProfile() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        txt_staffEmail.text = user?.email

        userreference?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val imageUrl = snapshot.child("image").value.toString()



                if(imageUrl != null){
                    displayImage(imageUrl, img_userprofile)
                }

                txt_staffFirstName.text = snapshot.child("firstname").value.toString()
                txt_staffLastName.text = snapshot.child("lastname").value.toString()
                    txt_staffDOB.text = snapshot.child("birthday").value.toString()
              txt_staffPosition.text = snapshot.child("position").value.toString()
                txt_staffEmployedDate.text = snapshot.child("employedDate").value.toString()
               txt_staffID.text = snapshot.child("staffID").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        btn_StaffLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
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
        return inflater.inflate(R.layout.fragment_user__profile_, container, false)
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            User_Profile_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadProfile()

        btn_changePic.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
        btn_uploadPic.setOnClickListener {
            uploadImageToFirebaseStorage()
        }
    }

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data!=null){
            //proceed and check what is the selected image
            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, selectedPhotoUri)
            img_userprofile.setImageBitmap(bitmap)
        }
    }

    private fun uploadImageToFirebaseStorage(){
        if(selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!).addOnSuccessListener {
            Log.d("RegisterActivity", "Successfully uploaded image:${it.metadata?.path}")

            //to get the image url from firebase storage
            ref.downloadUrl.addOnSuccessListener {
                Log.d("RegisterActivity", "FileLocation: $it")
                updateNewImageToFireBase(it.toString())
            }
        }
    }

    private fun updateNewImageToFireBase(profileImageUrl: String){
        val user = auth.currentUser
        databaseReference.child(user.uid).child("image").setValue(profileImageUrl).addOnSuccessListener {
            Log.d("Upload Image", "Successful uploaded new staff image")
        }

    }
}