package ch.heigvd.iict.daa.template.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import ch.heigvd.iict.daa.template.converter.Date
import ch.heigvd.iict.daa.template.models.Note
import ch.heigvd.iict.daa.template.models.Schedule
import ch.heigvd.iict.daa.template.repository.Dao
import kotlin.concurrent.thread

/**
 * Base de données Room pour gérer les notes et leurs plannings.
 */
@Database(entities = [Note::class, Schedule::class], version = 1, exportSchema = true)
@TypeConverters(Date::class)
abstract class NotesDB : RoomDatabase() {

    abstract fun dao(): Dao

    companion object {
        // Instance singleton de la base de données.
        private var INSTANCE: NotesDB? = null

        /**
         * Retourne l'instance unique de la base de données.
         */
        fun getDatabase(context: Context): NotesDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDB::class.java,
                    "notes_database.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        /**
         * Callback pour peupler la base de données lors de sa création.
         */
        private val DatabaseCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    thread { populateDatabase(database.dao()) }
                }
            }
        }

        /**
         * Remplit la base de données avec des données initiales si elle est vide.
         */
        private fun populateDatabase(dao: Dao) {
            if (!dao.isEmpty()) return

            repeat(10) {
                val note = Note.generateRandomNote()
                val noteId = dao.insert(note)

                Note.generateRandomSchedule()?.let { schedule ->
                    schedule.ownerId = noteId
                    dao.insert(schedule)
                }
            }
        }
    }
}
