package ch.heigvd.iict.daa.template.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.viewmodels.ViewModelNotes
import ch.heigvd.iict.daa.template.viewmodels.ViewModelNotesFactory

/**
 * Fragment offrant des actions de contrôle : générer et supprimer des notes.
 */
class ControlsFragment : Fragment() {

    // Instance du ViewModel pour gérer les données.
    private val viewModel: ViewModelNotes by activityViewModels {
        ViewModelNotesFactory((requireActivity().application as ch.heigvd.iict.daa.template.App).repository, requireActivity().application)
    }

    /**
     * Charge le layout du fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_controls, container, false)
    }

    /**
     * Configure les éléments de l'interface utilisateur et les écouteurs d'événements.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialisation des composants d'interface utilisateur.
        val notesCounter = view.findViewById<TextView>(R.id.text_view_notes_counter)
        val buttonGenerate = view.findViewById<View>(R.id.button_generate)
        val buttonDelete = view.findViewById<View>(R.id.button_delete)

        // Met à jour le compteur de notes en fonction des données observées.
        viewModel.countNotes.observe(viewLifecycleOwner) { count ->
            notesCounter.text = count.toString()
        }

        // Gère le clic pour générer une nouvelle note.
        buttonGenerate.setOnClickListener {
            viewModel.generateNote()
        }

        // Gère le clic pour supprimer toutes les notes.
        buttonDelete.setOnClickListener {
            viewModel.deleteNotes()
        }
    }
}
