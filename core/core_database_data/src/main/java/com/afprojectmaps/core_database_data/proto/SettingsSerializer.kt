package com.afprojectmaps.core_database_data.proto

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.core_database_data.Setting
import java.io.InputStream
import java.io.OutputStream

internal object SettingsSerializer:Serializer<Setting> {
    override val defaultValue: Setting
        get() = Setting.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Setting = try {
        Setting.parseFrom(input)
    }catch (e:Exception){
        throw CorruptionException("proto.", e)
    }

    override suspend fun writeTo(t: Setting, output: OutputStream) = t.writeTo(output)
}