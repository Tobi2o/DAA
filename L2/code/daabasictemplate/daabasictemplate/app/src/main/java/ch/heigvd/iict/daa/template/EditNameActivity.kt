package ch.heigvd.iict.daa.template


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditNameActivity : AppCompatActivity() {

    companion object {
        const val TAG = "EditNameActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_name)

        // Log pour suivre l'état du cycle de vie
        Log.d(TAG, "onCreate called")

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val buttonSaveName = findViewById<Button>(R.id.buttonSaveName)

        // Sauvegarde du prénom et retour à WelcomeActivity
        buttonSaveName.setOnClickListener {
            val intent = Intent()
            val newName = editTextName.text.toString()
            intent.putExtra(WelcomeActivity.EXTRA_NAME, newName)
            setResult(RESULT_OK, intent)
            finish()
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
