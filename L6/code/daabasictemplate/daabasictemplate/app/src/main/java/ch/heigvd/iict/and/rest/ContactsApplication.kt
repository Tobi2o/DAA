package ch.heigvd.iict.and.rest

import android.app.Application
import ch.heigvd.iict.and.rest.network.EnrollmentApiService
import ch.heigvd.iict.and.rest.database.ContactsDatabase

class ContactsApplication : Application() {
    private val database by lazy { ContactsDatabase.getDatabase(this) }
    private val apiService by lazy { EnrollmentApiService.create() }
    val repository by lazy { ContactsRepository(database.contactsDao(), apiService) }
}
