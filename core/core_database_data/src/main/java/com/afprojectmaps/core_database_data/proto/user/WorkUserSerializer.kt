package com.afprojectmaps.core_database_data.proto.user

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.core_database_data.WorkUser
import java.io.InputStream
import java.io.OutputStream

internal object WorkUserSerializer: Serializer<WorkUser> {
    override val defaultValue: WorkUser
        get() = WorkUser.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): WorkUser = try {
        WorkUser.parseFrom(input)
    }catch (e:Exception){
        throw CorruptionException("proto.", e)
    }

    override suspend fun writeTo(t: WorkUser, output: OutputStream) = t.writeTo(output)
}