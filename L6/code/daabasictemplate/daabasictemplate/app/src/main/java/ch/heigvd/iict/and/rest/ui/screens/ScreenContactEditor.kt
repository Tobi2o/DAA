package ch.heigvd.iict.and.rest.ui.screens

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import ch.heigvd.iict.and.rest.R
import ch.heigvd.iict.and.rest.models.Contact
import ch.heigvd.iict.and.rest.models.PhoneType
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContactEditor(
    contact: Contact?,
    onSave: (Contact) -> Unit,
    onCancel: () -> Unit,
    onDelete: (Contact) -> Unit
) {
    val context = LocalContext.current

    // Champs d'état mutable pour capturer les informations saisies par l'utilisateur
    var name by rememberSaveable { mutableStateOf(contact?.name.orEmpty()) }
    var firstName by rememberSaveable { mutableStateOf(contact?.firstname.orEmpty()) }
    var email by rememberSaveable { mutableStateOf(contact?.email.orEmpty()) }
    var phone by rememberSaveable { mutableStateOf(contact?.phoneNumber.orEmpty()) }
    var address by rememberSaveable { mutableStateOf(contact?.address.orEmpty()) }
    var zip by rememberSaveable { mutableStateOf(contact?.zip.orEmpty()) }
    var city by rememberSaveable { mutableStateOf(contact?.city.orEmpty()) }
    var type by rememberSaveable { mutableStateOf(contact?.type) }
    var birthday by rememberSaveable { mutableStateOf(contact?.birthday) }

    // Validation du numéro de téléphone au format suisse
    val isPhoneValid = remember(phone) {
        phone.matches(Regex("\\+41\\s\\d{2}\\s\\d{3}\\s\\d{2}\\s\\d{2}"))
    }

    // Validation globale du formulaire (nom obligatoire + numéro valide)
    val isFormValid = remember(name, phone, isPhoneValid) {
        name.isNotBlank() && isPhoneValid
    }

    // Fonction pour construire un objet Contact à partir des données saisies
    fun buildContact(): Contact {
        return Contact(
            id = contact?.id,
            name = name,
            firstname = firstName,
            email = email,
            phoneNumber = phone,
            address = address,
            zip = zip,
            city = city,
            type = type,
            birthday = birthday
        )
    }

    Scaffold(
        topBar = {
            // Barre supérieure avec un titre indiquant le mode (nouveau ou édition)
            TopAppBar(
                title = { Text(text = if (contact == null) "Nouveau Contact" else "Modifier Contact") }
            )
        },
        content = { padding ->
            // Conteneur principal avec gestion du défilement vertical
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = dimensionResource(R.dimen.form_field_padding))
                        .verticalScroll(rememberScrollState()), // Permet le défilement
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.form_field_spacing))
                ) {
                    // Champs de saisie pour les informations du contact
                    ValidatedTextField(
                        label = "Nom *",
                        value = name,
                        onValueChange = { name = it },
                        isError = name.isBlank(),
                        errorMessage = "Le nom est obligatoire"
                    )

                    ValidatedTextField(
                        label = "Prénom",
                        value = firstName,
                        onValueChange = { firstName = it }
                    )

                    ValidatedTextField(
                        label = "E-Mail",
                        value = email,
                        onValueChange = { email = it }
                    )

                    ValidatedTextField(label = "Adresse", value = address, onValueChange = { address = it })
                    ValidatedTextField(label = "Code Postal", value = zip, onValueChange = { zip = it })
                    ValidatedTextField(label = "Ville", value = city, onValueChange = { city = it })

                    // Champ de numéro de téléphone avec validation
                    ValidatedTextField(
                        label = "Numéro de Téléphone *",
                        value = phone,
                        onValueChange = { phone = it },
                        isError = phone.isBlank() || !isPhoneValid,
                        errorMessage = if (phone.isBlank()) {
                            "Le numéro de téléphone est obligatoire"
                        } else {
                            "Format du numéro invalide. Utilisez: +41 XX XXX XX XX"
                        },
                        placeholder = "+41 XX XXX XXXX" // Guide pour l'utilisateur
                    )

                    // Sélection du type de téléphone via des boutons radio
                    PhoneTypeRadioGroup(
                        label = "Type de Téléphone",
                        selectedType = type,
                        onTypeSelected = { type = it }
                    )

                    // Sélecteur de date pour la date de naissance
                    BirthdayPicker(
                        label = "Date de Naissance",
                        selectedDate = birthday,
                        onDateSelected = { birthday = it }
                    )

                    // Boutons d'action : Annuler, Supprimer, Sauvegarder
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Bouton "Annuler"
                        Button(
                            onClick = {
                                onCancel()
                                Toast.makeText(context, "Modification annulée", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.purple_500),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Annuler")
                        }

                        // Bouton "Supprimer" (uniquement en mode édition)
                        if (contact != null) {
                            Button(
                                onClick = {
                                    onDelete(contact)
                                    Toast.makeText(context, "Contact supprimé", Toast.LENGTH_SHORT).show()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.purple_500),
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Supprimer")
                            }
                        }

                        // Bouton "Sauvegarder" (activé seulement si le formulaire est valide)
                        Button(
                            onClick = {
                                val newContact = buildContact()
                                onSave(newContact)
                                Toast.makeText(context, "Contact sauvegardé", Toast.LENGTH_SHORT).show()
                            },
                            enabled = isFormValid,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.purple_500),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Sauvegarder")
                        }
                    }
                }
            }
        }
    )
}




// Composable pour sélectionner le type de téléphone à l'aide de boutons radio
@Composable
fun PhoneTypeRadioGroup(
    label: String,
    selectedType: PhoneType?,
    onTypeSelected: (PhoneType) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            PhoneType.values().forEach { phoneType ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedType == phoneType,
                        onClick = { onTypeSelected(phoneType) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(id = R.color.teal_200) // Use colorSecondary
                        )
                    )
                    Text(
                        text = phoneType.name,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

// Composable pour sélectionner une date de naissance à l'aide d'un sélecteur de date
@Composable
fun BirthdayPicker(
    label: String,
    selectedDate: Calendar?,
    onDateSelected: (Calendar) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val formattedDate = selectedDate?.let {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it.time)
    } ?: "Non spécifiée"

    if (showDialog) {
        val currentCalendar = selectedDate ?: Calendar.getInstance()
        DatePickerDialog(
            LocalContext.current,
            { _, year, month, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate.set(year, month, dayOfMonth)
                onDateSelected(newDate)
                showDialog = false
            },
            currentCalendar.get(Calendar.YEAR),
            currentCalendar.get(Calendar.MONTH),
            currentCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label : $formattedDate",
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = { showDialog = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.teal_200),
                contentColor = Color.White
            )
        ) {
            Text("Choisir")
        }
    }
}


// Composable réutilisable pour des champs de texte validés avec un placeholder et
// une validation personnalisée
@Composable
fun ValidatedTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String = "",
    placeholder: String? = null
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = { placeholder?.let { Text(it, color = Color.Gray) } },
            modifier = Modifier.fillMaxWidth(),
            isError = isError
        )
        if (isError) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}






