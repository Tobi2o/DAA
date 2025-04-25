package ch.heigvd.iict.daa.template.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.adapters.NotesAdapter
import ch.heigvd.iict.daa.template.databinding.FragmentNotesBinding
import ch.heigvd.iict.daa.template.models.NoteAndSchedule
import ch.heigvd.iict.daa.template.listener.OnClickListener
import ch.heigvd.iict.daa.template.viewmodels.ViewModelNotes
import ch.heigvd.iict.daa.template.viewmodels.ViewModelNotesFactory

/**
 * Fragment affichant une liste de notes et leurs éventuels plannings associés.
 */
class NotesFragment : Fragment(), OnClickListener {

    // Instance du ViewModel pour gérer les données.
    private val viewModel: ViewModelNotes by activityViewModels {
        ViewModelNotesFactory((requireActivity().application as ch.heigvd.iict.daa.template.App).repository, requireActivity().application)
    }

    // Binding pour accéder aux vues définies dans le layout.
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    // Adaptateur pour la RecyclerView.
    private lateinit var notesAdapter: NotesAdapter

    /**
     * Charge le layout du fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Configure la RecyclerView et observe les LiveData depuis le ViewModel.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialisation de l'adaptateur et configuration de la RecyclerView.
        notesAdapter = NotesAdapter(this)
        binding.recyclerViewNotes.apply {
            adapter = notesAdapter
            layoutManager = LinearLayoutManager(context)
        }

        // Observateur pour les données triées des notes.
        viewModel.sortedNotes.observe(viewLifecycleOwner) { notes ->
            notesAdapter.items = notes
        }
    }

    /**
     * Gestion des clics sur une note.
     *
     * @param noteAndSchedule La note et son planning associés.
     */
    override fun onNoteClicked(noteAndSchedule: NoteAndSchedule) {
        // Prépare le fragment d'édition pour afficher la note sélectionnée.
        val editNoteFragment = EditNoteFragment().apply {
            arguments = Bundle().apply {
                putLong("noteId", noteAndSchedule.note.noteId ?: -1L)
            }
        }

        // Identifie le conteneur à remplacer selon l'orientation.
        val containerId = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            R.id.fragment_container_controls
        } else {
            R.id.fragment_container_notes
        }

        // Effectue la transaction pour remplacer le fragment.
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(containerId, editNoteFragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    /**
     * Nettoie les ressources lorsque la vue est détruite.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
