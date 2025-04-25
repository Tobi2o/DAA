package ch.heigvd.iict.daa.template.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ch.heigvd.iict.daa.template.App
import ch.heigvd.iict.daa.template.databinding.FragmentNoteEditBinding
import ch.heigvd.iict.daa.template.models.NoteAndSchedule
import ch.heigvd.iict.daa.template.utils.DateUtils.formatDate
import ch.heigvd.iict.daa.template.viewmodels.ViewModelNotes
import ch.heigvd.iict.daa.template.viewmodels.ViewModelNotesFactory

/**
 * Fragment pour l'édition d'une note sélectionnée.
 */
class EditNoteFragment : Fragment() {
    private var _binding: FragmentNoteEditBinding? = null
    private val binding get() = _binding!!

    // Instance du ViewModel pour gérer les données.
    private val viewModel: ViewModelNotes by activityViewModels {
        ViewModelNotesFactory((requireActivity().application as App).repository, requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Récupération de l'ID de la note depuis les arguments.
        val noteId = arguments?.getLong("noteId", -1L) ?: -1L
        var noteDetails: NoteAndSchedule? = null

        // Observation des données de la note dans le ViewModel.
        viewModel.getNoteById(noteId).observe(viewLifecycleOwner) { note ->
            noteDetails = note
            note?.let {
                with(binding) {
                    editTextTitle.setText(it.note.title)
                    editTextContent.setText(it.note.text)
                    textViewDate.text = it.schedule?.date?.let { date -> formatDate(date) } ?: ""
                }
            }
        }

        // Gestion de l'action du bouton "Modifier".
        binding.buttonEdit.setOnClickListener {
            noteDetails?.let {
                viewModel.updateNoteAndSchedule(
                    noteId,
                    binding.editTextTitle.text.toString(),
                    binding.editTextContent.text.toString()
                )
                activity?.supportFragmentManager?.popBackStack()
            } ?: Toast.makeText(view.context, "Erreur lors de l'édition", Toast.LENGTH_LONG).show()
        }

        // Gestion de l'action du bouton "Annuler".
        binding.buttonCancel.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
