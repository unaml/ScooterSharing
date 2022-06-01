package dk.itu.moapd.scootersharing.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import dk.itu.moapd.scootersharing.R
import dk.itu.moapd.scootersharing.databinding.FragmentPaymentBinding
import dk.itu.moapd.scootersharing.models.TextValidator
import java.util.*

private const val ARG_ELAPSED = "elapsed"

class PaymentFragment : Fragment() {
    //View binding for ScooterSharingActivity
    private lateinit var paymentBinding: FragmentPaymentBinding
    private var elapsed: Long = -1
    /**
     * A component to validate the user's name.
     */
    private lateinit var nameValidator: TextValidator

    private lateinit var lastNameValidator: TextValidator

    /**
     * A component to validate the user's email.
     */
    private lateinit var phoneValidator: TextValidator

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
                nameValidator = TextValidator(firstName)
                firstName.addTextChangedListener(nameValidator)
                phoneValidator = TextValidator(phone)
                phone.addTextChangedListener(phoneValidator)
            payment.setOnClickListener {
                //check if the EditText have values or not
                if (!nameValidator.isValidName) {
                    Toast.makeText(requireContext(), "Enter valid first name", Toast.LENGTH_SHORT).show()
                }
                if (!phoneValidator.isValidPhone) {
                    Toast.makeText(requireContext(), "Enter valid last name", Toast.LENGTH_SHORT).show()
                }
                if(firstName.text.toString().trim().isNotEmpty() && lastName.text.toString().trim().isNotEmpty() && phone.text.toString().trim().isNotEmpty()
                    && dateofbirth.text.toString().trim().isNotEmpty() && cardNumber.text.toString().trim().isNotEmpty()
                    && expDate.text.toString().trim().isNotEmpty() && cvc.text.toString().trim().isNotEmpty()) {
                    loadingButton()
                }else{
                    Toast.makeText(requireContext(), "Please enter all fields ", Toast.LENGTH_SHORT).show()
                }
            }
            mobilepay.setOnClickListener {
                //check if the EditText have values or not
                if(firstName.text.toString().trim().isNotEmpty() && lastName.text.toString().trim().isNotEmpty() && phone.text.toString().trim().isNotEmpty()
                    && dateofbirth.text.toString().trim().isNotEmpty() && cardNumber.text.toString().trim().isNotEmpty()
                    && expDate.text.toString().trim().isNotEmpty() && cvc.text.toString().trim().isNotEmpty()) {
                    loadingButton()
                }else{
                    Toast.makeText(requireContext(), "Please enter all fields ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun FragmentPaymentBinding.loadingButton() {
        progressBar.visibility = View.VISIBLE
        Timer().schedule(object : TimerTask() {
            override fun run() {
                progressBar.visibility = View.INVISIBLE
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container_view_tag, ScooterSharingFragment())
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