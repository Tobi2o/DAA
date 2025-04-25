package ch.heigvd.iict.and.rest.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ch.heigvd.iict.and.rest.models.Contact

@Dao
interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contact: Contact): Long

    @Update
    fun update(contact: Contact)

    @Delete
    fun delete(contact: Contact)

    @Query("SELECT * FROM Contact WHERE syncState IN (:states)")
    suspend fun getContactsByState(vararg states: Contact.SyncState): List<Contact>

    @Query("SELECT * FROM Contact WHERE syncState IN (:states)")
    fun getContactsByStateLiveData(vararg states: Contact.SyncState): LiveData<List<Contact>>

    @Query("UPDATE Contact SET syncState = :newState WHERE id = :id")
    suspend fun updateSyncState(id: Long, newState: Contact.SyncState)

    @Query("DELETE FROM Contact")
    suspend fun clearAllContacts()

    @Query("SELECT * FROM Contact")
    fun getAllContactsLiveData() : LiveData<List<Contact>>

    @Query("SELECT * FROM Contact WHERE id = :id")
    fun getContactById(id : Long) : Contact?

    @Query("SELECT COUNT(*) FROM Contact")
    fun getCount() : Int

}
