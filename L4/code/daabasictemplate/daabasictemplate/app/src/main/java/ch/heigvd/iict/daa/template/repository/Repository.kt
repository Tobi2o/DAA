package ch.heigvd.iict.daa.template.repository

import androidx.lifecycle.LiveData
import ch.heigvd.iict.daa.template.models.Note
import ch.heigvd.iict.daa.template.models.NoteAndSchedule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Repository pour gérer les notes et leurs plannings.
 */
class Repository(private val dao: Dao, private val applicationScope: CoroutineScope) {

    // Récupère toutes les notes et plannings associés.
    var allNotes: LiveData<List<NoteAndSchedule>> = dao.getAllNotes()

    // Récupère le nombre total de notes.
    val countNotes: LiveData<Int> = dao.getCountNotes()

    /**
     * Supprime toutes les notes de la base de données.
     */
    fun deleteNotes() {
        applicationScope.launch {
            dao.deleteAllNotes()
        }
    }

    /**
     * Génère une nouvelle note et son planning aléatoire.
     */
    fun generateNote() {
        applicationScope.launch {
            val note = Note.generateRandomNote()
            val schedule = Note.generateRandomSchedule()

            val noteId = dao.insert(note)
            schedule?.let {
                it.ownerId = noteId
                dao.insert(it)
            }
        }
    }

    /**
     * Met à jour le titre et le texte d'une note existante.
     */
    fun editNote(noteId: Long, title: String, text: String) {
        applicationScope.launch {
            dao.updateNote(noteId, title, text)
        }
    }
}
