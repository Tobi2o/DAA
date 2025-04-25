package ch.heigvd.iict.and.rest.models

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    var name: String,
    var firstname: String?,
    var birthday: Calendar?,
    var email: String?,
    var address: String?,
    var zip: String?,
    var city: String?,
    var type: PhoneType?,
    var phoneNumber: String?,
    // Synchronization-specific fields
    var serverId: Long? = null, // ID côté serveur
    var syncState: SyncState = SyncState.CREATED // État de synchronisation
) {
    enum class SyncState {
        SYNCED, // Contact synchronisé avec le serveur
        CREATED, // Contact créé localement, pas encore synchronisé
        UPDATED, // Contact mis à jour localement, pas encore synchronisé
        DELETED // Contact supprimé localement, pas encore synchronisé
    }
}

data class ContactNetworkModel(
    var id: Long?,
    var name: String,
    var firstname: String?,
    var birthday: String?, // Format ISO 8601
    var email: String?,
    var address: String?,
    var zip: String?,
    var city: String?,
    var type: PhoneType?,
    var phoneNumber: String?
)

// Extensions pour la conversion entre Contact et ContactNetworkModel
fun Contact.toNetworkModel(): ContactNetworkModel = ContactNetworkModel(
    id = this.serverId,
    name = this.name,
    firstname = this.firstname,
    birthday = this.birthday?.time?.toInstant()?.toString(),
    email = this.email,
    address = this.address,
    zip = this.zip,
    city = this.city,
    type = this.type,
    phoneNumber = this.phoneNumber
)

fun ContactNetworkModel.toDomainModel(
    localId: Long? = null,
    syncState: Contact.SyncState = Contact.SyncState.SYNCED
): Contact = Contact(
    id = localId,
    serverId = this.id,
    name = this.name,
    firstname = this.firstname,
    birthday = this.birthday?.let {
        Calendar.getInstance().apply { time = java.util.Date.from(java.time.Instant.parse(it)) }
    },
    email = this.email,
    address = this.address,
    zip = this.zip,
    city = this.city,
    type = this.type,
    phoneNumber = this.phoneNumber,
    syncState = syncState
)
