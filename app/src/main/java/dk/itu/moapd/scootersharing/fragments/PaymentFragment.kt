package dk.itu.moapd.scootersharing.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dk.itu.moapd.scootersharing.databinding.FragmentPaymentBinding


private const val TAG = "PaymentFragment"

class PaymentFragment : Fragment() {

    //View binding for ScooterSharingActivity
    private lateinit var paymentBinding: FragmentPaymentBinding
    //private lateinit var ridesRecyclerView: RecyclerView
    //Setting up authentication
    //private lateinit var auth : FirebaseAuth
    //Setting up the database
    //private lateinit var database : DatabaseReference

    companion object {
        //private lateinit var adapter: ScooterArrayAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentPaymentBinding.inflate(inflater, container, false).let {
        paymentBinding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}