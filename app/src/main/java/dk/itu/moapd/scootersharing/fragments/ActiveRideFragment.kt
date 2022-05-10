package dk.itu.moapd.scootersharing.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import dk.itu.moapd.scootersharing.R
import dk.itu.moapd.scootersharing.R.id.fragment_container_view_tag
import dk.itu.moapd.scootersharing.databinding.FragmentActiveRideBinding
import dk.itu.moapd.scootersharing.services.ScooterService

class ActiveRideFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentActiveRideBinding
    private var broadcast = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            parentFragmentManager
                .beginTransaction()
                .replace(
                    fragment_container_view_tag, PaymentFragment.newInstance(
                        intent?.getLongExtra("elapsed", 0) ?: 0
                    )
                ).commit()
        }
    }

    override fun onResume() {
        super.onResume()
        requireContext().registerReceiver(broadcast, IntentFilter("finished"))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentActiveRideBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }.apply {
        with(binding) {
            endButton?.setOnClickListener(this@ActiveRideFragment)
        }
    }

    override fun onClick(ignored: View?) {
        requireContext().run {
            val intent = Intent(this, ScooterService::class.java).apply {
                putExtra("command", "end")
            }
            ContextCompat.startForegroundService(this, intent)
        }
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(broadcast)
    }
}