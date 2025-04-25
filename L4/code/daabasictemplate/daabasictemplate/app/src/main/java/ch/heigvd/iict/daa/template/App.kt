package ch.heigvd.iict.daa.template

import android.app.Application
import ch.heigvd.iict.daa.template.db.NotesDB
import ch.heigvd.iict.daa.template.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * Classe Application pour l'application de gestion de notes.
 * Initialise et fournit les dépendances globales de l'application.
 */
class App : Application() {

    // Définition du CoroutineScope pour toute l'application.
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Initialisation paresseuse du repository.
    // Le repository est créé uniquement lorsque nécessaire et une seule fois.
    val repository by lazy {
        // Création de l'instance de la base de données.
        val database = NotesDB.getDatabase(this)

        // Initialisation du repository avec le DAO et le CoroutineScope de l'application.
        Repository(database.dao(), applicationScope)
    }
}
