package dk.itu.moapd.scootersharing.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import android.widget.Toast
import dk.itu.moapd.scootersharing.R


class LoginActivity : AppCompatActivity() {

    private val signInLauncher =
        registerForActivityResult(
            FirebaseAuthUIActivityResultContract()
        ) { result -> onSignInResult(result) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createSignInIntent()
    }

    private fun createSignInIntent() {
        // Choose authentication providers.
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build())
            //AuthUI.IdpConfig.PhoneBuilder().build(),
            //AuthUI.IdpConfig.GoogleBuilder().build())
        // Create and launch sign-in intent.
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTheme(R.style.Theme_ScooterSharing)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(
        result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            toast("User logged in the app.")
            startMainActivity()
        } else
            toast("Authentication failed.")

    }
    private fun startMainActivity() {
        val intent = Intent(this,
            ScooterSharingActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Make a standard toast that just contains text.
     *
     * @param text The text to show. Can be formatted text.
     * @param duration How long to display the message. Either `Toast.LENGTH_SHORT` or
     *      `Toast.LENGTH_LONG`.
     */
    private fun toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, text, duration).show()
    }

}