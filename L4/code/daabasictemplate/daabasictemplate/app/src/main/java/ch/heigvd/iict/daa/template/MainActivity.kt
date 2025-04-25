package ch.heigvd.iict.daa.template

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.iict.daa.template.databinding.ActivityMainBinding
import ch.heigvd.iict.daa.template.sort.Sort
import ch.heigvd.iict.daa.template.viewmodels.ViewModelNotes
import ch.heigvd.iict.daa.template.viewmodels.ViewModelNotesFactory

/**
 * Activité principale de l'application de gestion de notes.
 * Gère l'interface utilisateur principale et les interactions avec le menu.
 */
class MainActivity : AppCompatActivity() {

    // Objet de liaison pour accéder aux vues définies dans activity_main.xml
    private lateinit var binding: ActivityMainBinding

    // ViewModel pour gérer les données et les interactions avec le repository
    private val viewModel: ViewModelNotes by viewModels {
        ViewModelNotesFactory((application as App).repository, applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisation du binding pour accéder au layout de l'activité
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Liaison du menu avec le fichier XML
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.creation_date_filter -> {
                viewModel.setSortOrder(Sort.BY_DATE)
                true
            }
            R.id.eta_filter -> {
                viewModel.setSortOrder(Sort.BY_ETA)
                true
            }
            R.id.menu_actions_generate -> {
                viewModel.generateNote()
                true
            }
            R.id.menu_actions_delete_all -> {
                viewModel.deleteNotes()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
