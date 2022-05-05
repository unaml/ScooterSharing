package dk.itu.moapd.scootersharing.models

import android.app.Application
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dk.itu.moapd.scootersharing.fragments.DATABASE_URL

//Firebase Realtime Database URL.
const val DATABASE_URL =
    "https://scootersharing-authentication-default-rtdb.europe-west1.firebasedatabase.app"

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.database(DATABASE_URL)
            .setPersistenceEnabled(true)
    }
}