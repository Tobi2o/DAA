package ch.heigvd.iict.and.rest

import android.util.Log
import ch.heigvd.iict.and.rest.network.EnrollmentApiService
import ch.heigvd.iict.and.rest.database.ContactsDao
import ch.heigvd.iict.and.rest.models.Contact
import ch.heigvd.iict.and.rest.models.toDomainModel
import ch.heigvd.iict.and.rest.models.toNetworkModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

// Classe responsable de la gestion des données des contacts : localement et via le serveur.
class ContactsRepository(
    private val contactsDao: ContactsDao, // DAO pour les interactions avec la base de données locale
    private val apiService: EnrollmentApiService // Service API pour les interactions avec le serveur
) {

    // LiveData contenant tous les contacts, observables dans l'application
    val allContacts = contactsDao.getAllContactsLiveData()

    /**
     * Sauvegarde ou met à jour un contact localement, puis tente de le synchroniser avec le serveur.
     */
    suspend fun saveContact(contact: Contact, uuid: UUID?) = withContext(Dispatchers.IO) {
        Log.d("ContactsRepository", "Starting saveContact for contact: $contact")

        // Déterminer l'état de synchronisation
        contact.syncState = if (contact.id == null) Contact.SyncState.CREATED else Contact.SyncState.UPDATED
        Log.d("ContactsRepository", "Contact syncState set to: ${contact.syncState}")

        // Insertion ou mise à jour locale
        if (contact.id == null) {
            contact.id = contactsDao.insert(contact)
            Log.d("ContactsRepository", "Inserted contact locally with id: ${contact.id}")
        } else {
            contactsDao.update(contact)
            Log.d("ContactsRepository", "Updated contact locally: $contact")
        }

        if (uuid == null) {
            Log.w("ContactsRepository", "UUID is null, skipping server synchronization.")
            return@withContext
        }

        try {
            // Synchronisation avec le serveur
            val response = if (contact.serverId != null) {
                // Mise à jour du contact sur le serveur
                Log.d("ContactsRepository", "Updating contact on server with serverId: ${contact.serverId}")
                apiService.updateContact(uuid, contact.serverId!!, contact.toNetworkModel())
            } else {
                // Création d'un nouveau contact sur le serveur
                Log.d("ContactsRepository", "Creating contact on server.")
                apiService.createContact(uuid, contact.toNetworkModel())
            }

            // Mise à jour locale après synchronisation réussie
            val serverContact = response.toDomainModel(contact.id)
            serverContact.syncState = Contact.SyncState.SYNCED
            contactsDao.update(serverContact)
            Log.d("ContactsRepository", "Synchronized contact with server and updated locally: $serverContact")
        } catch (e: Exception) {
            // Gestion des erreurs de synchronisation
            Log.e("ContactsRepository", "Failed to sync saveContact with server for contact: $contact", e)
            // Garder l'état CREATED ou UPDATED pour une synchronisation future
        }
    }

    /**
     * Supprime un contact localement et tente de le supprimer également sur le serveur.
     */
    suspend fun deleteContact(contact: Contact, uuid: UUID?) = withContext(Dispatchers.IO) {
        Log.d("ContactsRepository", "Starting deleteContact for contact: $contact")

        // Si le contact n'est pas synchronisé avec le serveur, supprimez-le directement
        if (contact.serverId == null) {
            Log.d("ContactsRepository", "Contact not synced with server. Deleting locally.")
            contactsDao.delete(contact)
            return@withContext
        }

        // Marquez le contact comme "DELETED" localement
        contact.syncState = Contact.SyncState.DELETED
        contactsDao.update(contact)
        Log.d("ContactsRepository", "Marked contact as DELETED locally: $contact")

        if (uuid == null) {
            Log.w("ContactsRepository", "UUID is null. Skipping server synchronization.")
            return@withContext
        }

        try {
            // Tentez de supprimer le contact sur le serveur
            Log.d("ContactsRepository", "Attempting to delete contact from server with serverId: ${contact.serverId}")
            apiService.deleteContact(uuid, contact.serverId!!)
            Log.d("ContactsRepository", "Contact deleted successfully on server.")

            // Supprimez localement après succès
            contactsDao.delete(contact)
            Log.d("ContactsRepository", "Deleted contact locally: $contact")
        } catch (e: Exception) {
            Log.e("ContactsRepository", "Failed to sync deleteContact with server for contact: $contact", e)

            // Si une erreur survient, garder l'état DELETED pour une synchronisation future
            Log.w("ContactsRepository", "Keeping contact marked as DELETED for future sync.")
        }
    }

    /**
     * Inscrit l'utilisateur auprès du serveur et renvoie un UUID unique.
     */
    suspend fun enroll(): UUID? = withContext(Dispatchers.IO) {
        try {
            Log.d("ContactsRepository", "Starting enrollment...")
            val uuid = apiService.enroll()
            Log.d("ContactsRepository", "Received UUID from server: $uuid")

            Log.d("ContactsRepository", "Clearing all local contacts...")
            contactsDao.clearAllContacts() // Supprime tous les contacts locaux
            Log.d("ContactsRepository", "Local contacts cleared.")

            Log.d("ContactsRepository", "Fetching contacts from server...")
            fetchContacts(uuid) // Récupère les contacts du serveur
            Log.d("ContactsRepository", "Fetched contacts successfully.")

            Log.d("ContactsRepository", "Enrollment completed successfully.")
            uuid
        } catch (e: Exception) {
            Log.e("ContactsRepository", "Enrollment failed", e)
            null
        }
    }

    /**
     * Récupère tous les contacts depuis le serveur et les insère localement.
     */
    private suspend fun fetchContacts(uuid: UUID) {
        try {
            val contacts = apiService.getContacts(uuid)
            contacts.forEach { contactDto ->
                contactsDao.insert(contactDto.toDomainModel()) // Conversion et insertion locale
            }
        } catch (e: Exception) {
            Log.e("ContactsRepository", "Failed to fetch contacts", e)
        }
    }

    /**
     * Synchronise tous les contacts avec le serveur.
     */
    suspend fun syncAllContacts(uuid: UUID?) = withContext(Dispatchers.IO) {
        if (uuid == null) {
            Log.w("ContactsRepository", "UUID is null, cannot synchronize contacts.")
            return@withContext
        }
        Log.d("ContactsRepository", "Starting full synchronization...")

        // Récupère tous les contacts avec des états CREATED, UPDATED ou DELETED
        contactsDao.getContactsByState(
            Contact.SyncState.CREATED,
            Contact.SyncState.UPDATED,
            Contact.SyncState.DELETED
        ).forEach { contact ->
            when (contact.syncState) {
                Contact.SyncState.CREATED, Contact.SyncState.UPDATED -> saveContact(contact, uuid)
                Contact.SyncState.DELETED -> deleteContact(contact, uuid)
                else -> Log.d("ContactsRepository", "Contact already synchronized: $contact")
            }
        }

        Log.d("ContactsRepository", "Full synchronization completed.")
    }
}
