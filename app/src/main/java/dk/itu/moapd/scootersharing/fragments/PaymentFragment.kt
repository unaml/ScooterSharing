package dk.itu.moapd.scootersharing.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dk.itu.moapd.scootersharing.R
import dk.itu.moapd.scootersharing.databinding.FragmentPaymentBinding
import java.util.*

private const val ARG_ELAPSED = "elapsed"

class PaymentFragment : Fragment() {
    //View binding for ScooterSharingActivity
    private lateinit var paymentBinding: FragmentPaymentBinding
    private var elapsed: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            elapsed = it.getLong(ARG_ELAPSED, 0)
        }
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
        with(paymentBinding) {
            payment.setOnClickListener {
                loadingButton()

            }
            mobilepay.setOnClickListener {
                loadingButton()
            }
        }
    }

    private fun FragmentPaymentBinding.loadingButton() {
        progressBar?.visibility = View.VISIBLE
        Timer().schedule(object : TimerTask() {
            override fun run() {
                progressBar?.visibility = View.INVISIBLE
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container_view_tag, ActiveRideFragment())
                    .commit()
            }
        }, 2000)

    }

    companion object {
        @JvmStatic
        fun newInstance(elapsed: Long) = PaymentFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_ELAPSED, elapsed)
            }
        }
    }
}