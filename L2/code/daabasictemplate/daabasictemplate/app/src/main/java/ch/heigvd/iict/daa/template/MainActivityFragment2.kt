package ch.heigvd.iict.daa.template

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import ch.heigvd.iict.daa.template.StepFragment

class MainActivityFragment2 : AppCompatActivity() {

    private var currentStep = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_fragment2)

        // Charger le premier fragment au démarrage
        if (savedInstanceState == null) {
            loadFragment(StepFragment.newInstance(currentStep))
        }

        // Bouton "Next" pour passer à l'étape suivante
        findViewById<Button>(R.id.buttonNext).setOnClickListener {
            currentStep++
            loadFragment(StepFragment.newInstance(currentStep))
        }

        // Bouton "Back" pour revenir au fragment précédent
        findViewById<Button>(R.id.buttonBack).setOnClickListener {
            if (currentStep > 0) {
                supportFragmentManager.popBackStack()
                currentStep--
            } else {
                finish() // Si pas de fragments précédents, quitter l'activité
            }
        }

        // Bouton "Close" pour fermer l'activité
        findViewById<Button>(R.id.buttonClose).setOnClickListener {
            finish()
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}
