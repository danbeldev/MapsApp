package com.afprojectmaps.core_network_domain.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalSerializationApi
@Serializer(forClass = String::class)
internal object DateWeekSerialization : KSerializer<String> {

    @Suppress("SimpleDateFormat")
    private val df = SimpleDateFormat("EEEE")
    override fun deserialize(decoder: Decoder): String {
        val netDate = Date(java.lang.Long.parseLong(decoder.decodeInt().toString()) * 1000)
        return df.format(netDate)
    }

    override fun serialize(encoder: Encoder, value: String) {
        encoder.encodeString(df.format(value))
    }
}