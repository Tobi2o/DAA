package ch.heigvd.iict.daa.template

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NAME = "EXTRA_NAME"
        const val TAG = "WelcomeActivity"
    }

    // Enregistrer l'ActivityResultLauncher pour gérer les résultats
    private val editNameLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val newName = data?.getStringExtra(EXTRA_NAME)

            // Utiliser les ressources pour afficher un message dynamique sans concaténation
            val textViewWelcome = findViewById<TextView>(R.id.textViewWelcome)
            val welcomeMessage = getString(R.string.welcome_message, newName)
            textViewWelcome.text = welcomeMessage
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Log pour suivre l'état du cycle de vie
        Log.d(TAG, "onCreate called")

        val buttonEditName = findViewById<Button>(R.id.buttonEditName)

        // Lancement de l'activité EditNameActivity pour modifier le nom via la nouvelle API
        buttonEditName.setOnClickListener {
            val intent = Intent(this, EditNameActivity::class.java)
            editNameLauncher.launch(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }
}
