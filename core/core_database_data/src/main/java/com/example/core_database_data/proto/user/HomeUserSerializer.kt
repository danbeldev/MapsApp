package com.example.core_database_data.proto.user

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.core_database_data.HomeUser
import java.io.InputStream
import java.io.OutputStream

internal object HomeUserSerializer: Serializer<HomeUser> {
    override val defaultValue: HomeUser
        get() = HomeUser.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): HomeUser = try {
        HomeUser.parseFrom(input)
    }catch (e:Exception){
        throw CorruptionException("proto.", e)
    }

    override suspend fun writeTo(t: HomeUser, output: OutputStream) = t.writeTo(output)
}