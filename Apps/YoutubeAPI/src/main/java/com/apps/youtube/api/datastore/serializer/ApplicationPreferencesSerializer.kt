package com.apps.youtube.api.datastore.serializer

import com.apps.youtube.api.datastore.models.ApplicationPreferences
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

/**
 * Serializer for ApplicationPreferences using JSON format
 */
object ApplicationPreferencesSerializer : Serializer<ApplicationPreferences> {

    private val jsonFormat = Json { ignoreUnknownKeys = true }

    /**
     * Default value for ApplicationPreferences when no data exists
     */
    override val defaultValue: ApplicationPreferences
        get() = ApplicationPreferences()


    /**
     * Reads ApplicationPreferences from input stream
     * @throws CorruptionException if data cannot be deserialized
     */
    override suspend fun readFrom(input: InputStream): ApplicationPreferences {
        try {
            return jsonFormat.decodeFromString(
                deserializer = ApplicationPreferences.serializer(),
                string = input.readBytes().decodeToString(),
            )
        } catch (exception: SerializationException) {
            throw CorruptionException("Cannot read datastore", exception)
        }
    }

    /**
     * Writes ApplicationPreferences to output stream in JSON format
     * @param t ApplicationPreferences to serialize
     * @param output Target output stream
     */
    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: ApplicationPreferences, output: OutputStream) {
        output.write(
            jsonFormat.encodeToString(
                serializer = ApplicationPreferences.serializer(),
                value = t,
            ).encodeToByteArray(),
        )
    }
}
