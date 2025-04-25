package ch.heigvd.iict.and.rest

import ch.heigvd.iict.and.rest.models.Contact
import ch.heigvd.iict.and.rest.models.ContactNetworkModel
import ch.heigvd.iict.and.rest.models.PhoneType
import ch.heigvd.iict.and.rest.models.toDomainModel
import ch.heigvd.iict.and.rest.models.toNetworkModel
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar
import java.util.GregorianCalendar

class ContactEntityTest {

    @Test
    fun testContactToNetworkModelConversion() {
        // Arrange
        val calendar = GregorianCalendar(2000, Calendar.JANUARY, 1)
        val contact = Contact(
            id = 1,
            name = "John",
            firstname = "Doe",
            birthday = calendar,
            email = "john.doe@example.com",
            address = "123 Main St",
            zip = "12345",
            city = "Somewhere",
            type = PhoneType.HOME,
            phoneNumber = "5551234567",
            serverId = 100,
            syncState = Contact.SyncState.CREATED
        )

        // Act
        val dto = contact.toNetworkModel()

        // Assert
        assertEquals(contact.serverId, dto.id)
        assertEquals(contact.name, dto.name)
        assertEquals(contact.firstname, dto.firstname)
        assertEquals(contact.birthday?.time?.toInstant()?.toString(), dto.birthday)
        assertEquals(contact.email, dto.email)
        assertEquals(contact.address, dto.address)
        assertEquals(contact.zip, dto.zip)
        assertEquals(contact.city, dto.city)
        assertEquals(contact.type, dto.type)
        assertEquals(contact.phoneNumber, dto.phoneNumber)
    }

    @Test
    fun testContactNetworkModelToDomainModelConversion() {
        // Arrange
        val birthday = "2000-01-01T00:00:00Z"
        val dto = ContactNetworkModel(
            id = 100,
            name = "John",
            firstname = "Doe",
            birthday = birthday,
            email = "john.doe@example.com",
            address = "123 Main St",
            zip = "12345",
            city = "Somewhere",
            type = PhoneType.HOME,
            phoneNumber = "5551234567"
        )

        // Act
        val contact = dto.toDomainModel(localId = 1, syncState = Contact.SyncState.SYNCED)

        // Assert
        assertEquals(dto.id, contact.serverId)
        assertEquals(dto.name, contact.name)
        assertEquals(dto.firstname, contact.firstname)
        assertEquals(birthday, contact.birthday?.time?.toInstant()?.toString())
        assertEquals(dto.email, contact.email)
        assertEquals(dto.address, contact.address)
        assertEquals(dto.zip, contact.zip)
        assertEquals(dto.city, contact.city)
        assertEquals(dto.type, contact.type)
        assertEquals(dto.phoneNumber, contact.phoneNumber)
        assertEquals(Contact.SyncState.SYNCED, contact.syncState)
    }
}
