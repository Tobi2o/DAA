package ch.heigvd.iict.daa.template.viewmodels

import android.content.Context
import androidx.lifecycle.*

import ch.heigvd.iict.daa.template.sort.Sort
import ch.heigvd.iict.daa.template.models.NoteAndSchedule
import ch.heigvd.iict.daa.template.repository.Repository

/**
 * ViewModel responsable de la gestion des données de notes et de leur interaction avec l'interface utilisateur.
 */
class ViewModelNotes(private val repository: Repository, context: Context) : ViewModel() {

    // SharedPreferences pour stocker l'ordre de tri.
    private val sharedPreferences = context.getSharedPreferences("NotePreferences", Context.MODE_PRIVATE)

    // LiveData pour gérer les changements de l'ordre de tri.
    private val _sortOrder = MutableLiveData<Sort>().apply {
        value = Sort.values()[sharedPreferences.getInt("sortOrder", Sort.NONE.ordinal)]
    }
    private val sortOrder: LiveData<Sort> = _sortOrder

    // LiveData contenant toutes les notes.
    private val allNotes: LiveData<List<NoteAndSchedule>> = repository.allNotes

    // LiveData contenant le nombre total de notes.
    val countNotes: LiveData<Int> = repository.countNotes

    // Notes triées par date de création.
    private val sortedNotesByDate: LiveData<List<NoteAndSchedule>> = allNotes.map { notes ->
        notes.sortedByDescending { note -> note.note.creationDate }
    }

    // Notes triées par date prévue.
    private val sortedNotesByEta: LiveData<List<NoteAndSchedule>> = allNotes.map { notes ->
        notes.sortedWith(compareBy(nullsLast()) { note -> note.schedule?.date })
    }

    // Notes triées selon l'ordre actuel.
    val sortedNotes: LiveData<List<NoteAndSchedule>> = sortOrder.switchMap {
        when (it) {
            Sort.BY_DATE -> sortedNotesByDate
            Sort.BY_ETA -> sortedNotesByEta
            else -> allNotes
        }
    }

    /**
     * Modifie l'ordre de tri et le sauvegarde dans les SharedPreferences.
     */
    fun setSortOrder(sortOrder: Sort) {
        _sortOrder.value = sortOrder
        with(sharedPreferences.edit()) {
            putInt("sortOrder", sortOrder.ordinal)
            apply()
        }
    }

    /**
     * Génère une nouvelle note aléatoire.
     */
    fun generateNote() {
        repository.generateNote()
    }

    /**
     * Supprime toutes les notes.
     */
    fun deleteNotes() {
        repository.deleteNotes()
    }

    /**
     * Récupère une note spécifique par son ID.
     */
    fun getNoteById(noteId: Long): LiveData<NoteAndSchedule?> {
        return allNotes.switchMap { notes ->
            MutableLiveData(notes.find { it.note.noteId == noteId })
        }
    }

    /**
     * Met à jour le titre et le texte d'une note existante.
     */
    fun updateNoteAndSchedule(noteId: Long, title: String, text: String) {
        repository.editNote(noteId, title, text)
    }
}

/**
 * Factory pour créer des instances de ViewModelNotes.
 */
class ViewModelNotesFactory(private val repository: Repository, private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelNotes::class.java)) {
            return ViewModelNotes(repository, context) as T
        }
        throw IllegalArgumentException("Classe ViewModel inconnue")
    }
}
