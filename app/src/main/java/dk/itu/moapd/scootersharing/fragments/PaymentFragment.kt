package dk.itu.moapd.scootersharing.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import dk.itu.moapd.scootersharing.R
import dk.itu.moapd.scootersharing.adapters.ScooterArrayAdapter
import dk.itu.moapd.scootersharing.databinding.FragmentPaymentBinding
import dk.itu.moapd.scootersharing.databinding.FragmentScooterSharingBinding

private const val TAG = "PaymentFragment"

class PaymentFragment : Fragment() {

    //View binding for ScooterSharingActivity
    private var _binding : FragmentPaymentBinding? = null
    private lateinit var ridesRecyclerView: RecyclerView
    //Setting up authentication
    private lateinit var auth : FirebaseAuth
    //Setting up the database
    private lateinit var database : DatabaseReference

    /**
     * This property is only valid between `onCreateView()` and `onDestroyView()` methods.
     */
    private val binding get() = _binding!!

    companion object {
        private lateinit var adapter: ScooterArrayAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}