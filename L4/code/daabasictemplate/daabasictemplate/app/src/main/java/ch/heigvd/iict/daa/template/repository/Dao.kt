package ch.heigvd.iict.daa.template.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ch.heigvd.iict.daa.template.models.Note
import ch.heigvd.iict.daa.template.models.NoteAndSchedule
import ch.heigvd.iict.daa.template.models.Schedule

/**
 * DAO (Data Access Object) pour gérer les interactions avec la base de données Room.
 */
@Dao
interface Dao {
    @Transaction
    @Query("SELECT * FROM note")
    fun getAllNotes(): LiveData<List<NoteAndSchedule>>

    @Query("DELETE FROM note")
    fun deleteAllNotes()

    @Query("SELECT COUNT(*) FROM note")
    fun getCountNotes(): LiveData<Int>

    @Query("SELECT EXISTS (SELECT 1 FROM note LIMIT 1)")
    fun isEmpty(): Boolean

    @Insert
    fun insert(note: Note): Long

    @Insert
    fun insert(schedule: Schedule): Long

    @Query("UPDATE note SET title = :title, text = :text WHERE noteId = :noteId")
    fun updateNote(noteId: Long, title: String, text: String)
}
