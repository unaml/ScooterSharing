package dk.itu.moapd.scootersharing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
//import dk.itu.moapd.scootersharing.databinding.ActivityMainBinding
//import dk.itu.moapd.scootersharing.databinding.ResultLayoutBinding
//import dk.itu.moapd.scootersharing.databinding.ButtonsLayoutBinding

class ScooterSharingActivity : AppCompatActivity() {

    //GUI variables
    private lateinit var lastAddedText : EditText
    private lateinit var addButton : Button
    private lateinit var nameText : TextView
    private lateinit var whereText : TextView
    private val scooter : Scooter = Scooter ("", "", 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scooter_sharing)

        //Edit texts
        lastAddedText = findViewById(R.id.last_added_text)
        whereText = findViewById(R.id.where_text)
        nameText = findViewById(R.id.name_text)
        //Buttons
        addButton = findViewById(R.id.add_button)
        addButton.setOnClickListener{
         if(nameText.text.isNotEmpty() && whereText.text.isNotEmpty()){
             //Update the object attributes
             val name = nameText.text.toString().trim()
             val where = whereText.text.toString().trim()
             scooter.name = name
             scooter.where = where
             //Reset
             nameText.setText("")
             whereText.setText("")
             updateUi()
            }
        }
    }
    private fun updateUi () {
        lastAddedText.setText(scooter.toString () )
    }
}