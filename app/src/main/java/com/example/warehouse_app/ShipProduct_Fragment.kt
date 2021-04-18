package com.example.warehouse_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.warehouse_app.ShipProduct_FragmentDirections.Companion.actionShipProductFragmentToItemDetailsShipmentFragment
import kotlinx.android.synthetic.main.fragment_home_page.*
import kotlinx.android.synthetic.main.fragment_ship_product_.*
import kotlinx.android.synthetic.main.fragment_shipment.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShipProduct_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShipProduct_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ship_product_, container, false)

    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShipProduct_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    //navigate to another page
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**btn_Search.setOnClickListener{
            val action = ShipProduct_FragmentDirections.actionShipProductFragmentToItemDetailsShipmentFragment()
            findNavController().navigate(action)
        }**/

        btn_Search.setOnClickListener{
            val amount = editTextTextItemID.text.toString()
            val bundle = bundleOf("amount" to amount)
            val action = ShipProduct_FragmentDirections.actionShipProductFragmentToItemDetailsShipmentFragment(
               amount
            )
            findNavController().navigate(action)
        }

    }

}



