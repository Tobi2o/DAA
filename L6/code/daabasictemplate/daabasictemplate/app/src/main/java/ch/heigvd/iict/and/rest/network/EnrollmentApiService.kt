package ch.heigvd.iict.and.rest.network

import ch.heigvd.iict.and.rest.models.ContactNetworkModel
import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.time.OffsetDateTime
import java.util.UUID

private const val HEADER_UUID = "X-UUID"

interface EnrollmentApiService {
    @GET("enroll")
    suspend fun enroll(): UUID

    @GET("contacts")
    suspend fun getContacts(@Header(HEADER_UUID) uuid: UUID): List<ContactNetworkModel>

    @POST("contacts")
    suspend fun createContact(
        @Header(HEADER_UUID) uuid: UUID,
        @Body contact: ContactNetworkModel
    ): ContactNetworkModel

    @PUT("contacts/{id}")
    suspend fun updateContact(
        @Header(HEADER_UUID) uuid: UUID,
        @Path("id") contactId: Long,
        @Body contact: ContactNetworkModel
    ): ContactNetworkModel

    @DELETE("contacts/{id}")
    suspend fun deleteContact(
        @Header(HEADER_UUID) uuid: UUID,
        @Path("id") contactId: Long
    ): Response<Unit>

    companion object {
        fun create(baseUrl: String = "https://daa.iict.ch/"): EnrollmentApiService {

            val gson = GsonBuilder()
                .registerTypeAdapter(OffsetDateTime::class.java, OffsetDateTimeGsonAdapter)
                .create()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(RetrofitTypeAdapter)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(EnrollmentApiService::class.java)
        }
    }
}