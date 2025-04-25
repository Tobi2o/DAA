package ch.heigvd.iict.and.rest.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ch.heigvd.iict.and.rest.ContactsApplication
import ch.heigvd.iict.and.rest.R
import ch.heigvd.iict.and.rest.models.Contact
import ch.heigvd.iict.and.rest.viewmodels.ContactsViewModel
import ch.heigvd.iict.and.rest.viewmodels.ContactsViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContact(
    application: ContactsApplication,
    contactsViewModel: ContactsViewModel = viewModel(factory = ContactsViewModelFactory(application))
) {
    // Context for Toasts
    val context = LocalContext.current

    // Observe LiveData from the ViewModel
    val contacts: List<Contact> by contactsViewModel.allContacts.observeAsState(initial = emptyList())
    val isEditing by contactsViewModel.isEditing.observeAsState(false)
    val selectedContact by contactsViewModel.selectedContact.observeAsState()

    // Main scaffold
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    if (isEditing) { // Ajoute une flèche de retour uniquement en mode édition
                        IconButton(onClick = { contactsViewModel.stopEditing() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_arrow_back_24), // Flèche de retour
                                contentDescription = "Retour",
                                tint = MaterialTheme.colorScheme.onPrimary // Utilisation de `colorOnPrimary`
                            )
                        }
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        color = MaterialTheme.colorScheme.onPrimary, // Utilisation de `colorOnPrimary`
                        modifier = Modifier.padding(start = if (isEditing) 8.dp else 0.dp) // Décale légèrement le titre
                    )
                },
                actions = {
                    // Action pour ajouter des contacts
                    IconButton(onClick = {
                        contactsViewModel.enroll()
                        Toast.makeText(context, "Requête d'enrôlement envoyée.", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.populate),
                            contentDescription = "Ajouter des contacts",
                            tint = MaterialTheme.colorScheme.onPrimary // Utilisation de `colorOnPrimary`
                        )
                    }

                    // Action pour synchroniser les contacts
                    IconButton(onClick = {
                        contactsViewModel.refresh()
                        Toast.makeText(context, "Synchronisation des contacts...", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.synchronize),
                            contentDescription = "Synchroniser",
                            tint = MaterialTheme.colorScheme.onPrimary // Utilisation de `colorOnPrimary`
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.purple_500) // Utilisation de `colorPrimary`
                )
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            // Bouton flottant pour créer un nouveau contact (uniquement en mode liste)
            if (!isEditing) {
                FloatingActionButton(
                    onClick = { contactsViewModel.startEditing(null) },
                    containerColor = colorResource(id = R.color.purple_500), // Utilisation de `colorPrimary`
                    contentColor = MaterialTheme.colorScheme.onPrimary // Utilisation de `colorOnPrimary`
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Créer un contact")
                }
            }
        }
    ) { padding ->
        // Navigation entre les écrans
        Column(modifier = Modifier.padding(top = padding.calculateTopPadding())) {
            if (isEditing) {
                // Affiche l'éditeur de contact en mode édition
                ScreenContactEditor(
                    contact = selectedContact,
                    onSave = { contact ->
                        if (contact.id == null) {
                            contactsViewModel.create(contact)
                            Toast.makeText(context, "Contact créé avec succès.", Toast.LENGTH_SHORT).show()
                        } else {
                            contactsViewModel.update(contact)
                        }
                        contactsViewModel.stopEditing()
                    },
                    onCancel = {
                        contactsViewModel.stopEditing()
                        Toast.makeText(context, "Édition annulée.", Toast.LENGTH_SHORT).show()
                    },
                    onDelete = { contact ->
                        contactsViewModel.delete(contact)
                        contactsViewModel.stopEditing()
                        Toast.makeText(context, "Contact supprimé.", Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                // Affiche la liste des contacts en mode liste
                ScreenContactList(
                    contacts = contacts,
                    onContactSelected = { selectedContact ->
                        contactsViewModel.startEditing(selectedContact)
                    }
                )
            }
        }
    }
}


