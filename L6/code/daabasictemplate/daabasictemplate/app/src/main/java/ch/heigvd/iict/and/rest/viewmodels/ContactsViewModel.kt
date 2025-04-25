package ch.heigvd.iict.and.rest.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ch.heigvd.iict.and.rest.ContactsApplication
import ch.heigvd.iict.and.rest.models.Contact
import kotlinx.coroutines.launch
import java.util.UUID

// Nom du fichier des préférences partagées (SharedPreferences) utilisé pour stocker le UUID
private const val CONTACTS_PREFS_NAME = "contacts_prefs"
// Clé utilisée pour enregistrer le UUID dans SharedPreferences
private const val CONTACTS_PREFS_KEY_UUID = "enrollment_uuid"

// Fonction d'extension pour sauvegarder le UUID dans SharedPreferences
fun Context.saveUuid(uuid: UUID?) {
    val prefs = getSharedPreferences(CONTACTS_PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().putString(CONTACTS_PREFS_KEY_UUID, uuid?.toString()).apply()
}

// Fonction d'extension pour charger le UUID depuis SharedPreferences
fun Context.loadUuid(): UUID? {
    val prefs = getSharedPreferences(CONTACTS_PREFS_NAME, Context.MODE_PRIVATE)
    val uuidString = prefs.getString(CONTACTS_PREFS_KEY_UUID, null)
    return uuidString?.let { UUID.fromString(it) } // Convertit la chaîne en UUID si elle existe
}

// ViewModel pour gérer les données des contacts
class ContactsViewModel(application: ContactsApplication) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak") // Suppression de l'avertissement pour l'utilisation d'un contexte statique
    private val context = application.applicationContext // Contexte de l'application

    // UUID chargé depuis SharedPreferences
    private var uuid: UUID? = context.loadUuid()

    // Références au repository pour accéder aux opérations de données
    private val repository = application.repository

    // Liste observable de tous les contacts
    val allContacts: LiveData<List<Contact>> = repository.allContacts

    // Contact actuellement sélectionné (pour l'édition)
    private val _selectedContact = MutableLiveData<Contact?>(null)
    val selectedContact: LiveData<Contact?> get() = _selectedContact

    // Indicateur si un contact est en mode édition
    private val _isEditing = MutableLiveData(false)
    val isEditing: LiveData<Boolean> get() = _isEditing

    // Démarre l'édition d'un contact donné
    fun startEditing(contact: Contact?) {
        _selectedContact.value = contact
        _isEditing.value = true
    }

    // Arrête l'édition
    fun stopEditing() {
        _selectedContact.value = null
        _isEditing.value = false
    }

    // Inscription (enrollment) auprès du serveur pour obtenir un UUID
    fun enroll() {
        viewModelScope.launch {
            val newUuid = repository.enroll() // Appelle le repository pour effectuer l'inscription
            if (newUuid != null) {
                uuid = newUuid
                context.saveUuid(newUuid) // Sauvegarde le UUID dans SharedPreferences
            }
        }
    }

    // Synchronise tous les contacts avec le serveur
    fun refresh() {
        viewModelScope.launch {
            repository.syncAllContacts(uuid) // Appelle la méthode de synchronisation du repository
        }
    }

    // Crée un nouveau contact et le sauvegarde localement et sur le serveur
    fun create(contact: Contact) {
        viewModelScope.launch {
            repository.saveContact(contact, uuid) // Sauvegarde le contact
        }
    }

    // Met à jour un contact existant
    fun update(contact: Contact) {
        viewModelScope.launch {
            repository.saveContact(contact, uuid) // Met à jour le contact
        }
    }

    // Supprime un contact
    fun delete(contact: Contact) {
        viewModelScope.launch {
            repository.deleteContact(contact, uuid) // Supprime le contact localement et tente de le supprimer sur le serveur
        }
    }
}

class ContactsViewModelFactory(private val application: ContactsApplication) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
