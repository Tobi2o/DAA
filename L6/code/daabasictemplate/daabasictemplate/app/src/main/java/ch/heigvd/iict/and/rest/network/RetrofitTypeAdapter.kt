package ch.heigvd.iict.and.rest.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

/**
 * Factory pour gérer les conversions de types personnalisées avec Retrofit.
 */
object RetrofitTypeAdapter : Converter.Factory() {

    /**
     * Convertit le corps d'une réponse en un type spécifique (ici, UUID).
     */
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return when (type) {
            UUID::class.java -> UUIDResponseHandler
            else -> null
        }
    }

    /**
     * Convertit un type spécifique (ici, UUID) en un corps de requête HTTP.
     */
    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return when (type) {
            UUID::class.java -> UUIDRequestHandler
            else -> null
        }
    }

    /**
     * Convertit un type spécifique (ici, UUID) en chaîne de caractères.
     */
    override fun stringConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? {
        return when (type) {
            UUID::class.java -> UUIDToStringHandler
            else -> null
        }
    }
}

/**
 * Gestionnaire pour convertir un UUID en chaîne de caractères pour les requêtes HTTP.
 */
object UUIDToStringHandler : Converter<UUID, String> {
    override fun convert(value: UUID): String = value.toString()
}

/**
 * Gestionnaire pour convertir le corps d'une réponse en UUID.
 */
object UUIDResponseHandler : Converter<ResponseBody, UUID> {
    override fun convert(value: ResponseBody): UUID {
        return UUID.fromString(value.string().trim())
    }
}

/**
 * Gestionnaire pour convertir un UUID en RequestBody pour les requêtes HTTP.
 */
object UUIDRequestHandler : Converter<UUID, RequestBody> {
    private val mediaType = "text/plain; charset=UTF-8".toMediaType()

    override fun convert(value: UUID): RequestBody {
        return value.toString().toRequestBody(mediaType)
    }
}

/**
 * Adaptateur Gson pour la sérialisation et la désérialisation de OffsetDateTime.
 */
object OffsetDateTimeGsonAdapter : JsonSerializer<OffsetDateTime>, JsonDeserializer<OffsetDateTime> {
    private val isoFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    /**
     * Sérialise un OffsetDateTime en chaîne de caractères au format ISO 8601.
     */
    override fun serialize(
        src: OffsetDateTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src?.format(isoFormatter))
    }

    /**
     * Désérialise une chaîne de caractères au format ISO 8601 en OffsetDateTime.
     */
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): OffsetDateTime {
        return OffsetDateTime.parse(json?.asString, isoFormatter)
    }
}
