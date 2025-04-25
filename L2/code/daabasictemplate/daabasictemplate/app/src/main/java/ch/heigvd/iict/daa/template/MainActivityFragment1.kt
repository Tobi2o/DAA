package ch.heigvd.iict.daa.template

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ch.heigvd.iict.and.labo2.fragments.ColorFragment
import ch.heigvd.iict.and.labo2.fragments.CounterFragment

class MainActivityFragment1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_fragment1)

        // Ajouter CounterFragment dans son conteneur
        supportFragmentManager.beginTransaction()
            .replace(R.id.counterFragmentContainer, CounterFragment())
            .commit()

        // Ajouter ColorFragment dans son conteneur
        supportFragmentManager.beginTransaction()
            .replace(R.id.colorFragmentContainer, ColorFragment())
            .commit()
    }
}
