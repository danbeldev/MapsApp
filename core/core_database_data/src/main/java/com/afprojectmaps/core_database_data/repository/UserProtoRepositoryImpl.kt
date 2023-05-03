package com.afprojectmaps.core_database_data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.afprojectmaps.core_database_data.proto.user.HomeUserSerializer
import com.afprojectmaps.core_database_data.proto.user.WorkUserSerializer
import com.afprojectmaps.core_database_domain.model.HomeUser
import com.afprojectmaps.core_database_domain.model.WorkUser
import com.afprojectmaps.core_database_domain.repository.UserProtoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserProtoRepositoryImpl @Inject constructor(
    private val context: Context
):UserProtoRepository {
    override fun getHomeUser(): Flow<HomeUser> = context.userHomeDataStore.data
        .catch { exception ->
            if (exception is IOException){
                emit(com.example.core_database_data.HomeUser.getDefaultInstance())
            }else{
                throw exception
            }
        }.map {
            HomeUser(
                homeName = it.homeName,
                homeLat = it.homeLat,
                homeLon = it.homeLon
            )
        }

    override fun getWorkUser(): Flow<WorkUser> = context.userWorkDataStore.data
        .catch { exception ->
            if (exception is IOException){
                emit(com.example.core_database_data.WorkUser.getDefaultInstance())
            }else{
                throw exception
            }
        }.map {
            WorkUser(
                workName = it.workName,
                workLat = it.workLat,
                workLon = it.workLon
            )
        }

    override suspend fun updateHomeUser(homeUser: HomeUser) {
        context.userHomeDataStore.updateData {
            it.toBuilder()
                .setHomeName(homeUser.homeName)
                .setHomeLon(homeUser.homeLon)
                .setHomeLat(homeUser.homeLat)
                .build()
        }
    }

    override suspend fun updateWorkUser(workUser: WorkUser) {
        context.userWorkDataStore.updateData {
            it.toBuilder()
                .setWorkName(workUser.workName)
                .setWorkLon(workUser.workLon)
                .setWorkLat(workUser.workLat)
                .build()
        }
    }

    companion object{
        private val Context.userHomeDataStore: DataStore<com.example.core_database_data.HomeUser>
        by dataStore(
            fileName = "user_home_data_store",
            serializer = HomeUserSerializer
        )

        private val Context.userWorkDataStore: DataStore<com.example.core_database_data.WorkUser>
        by dataStore(
            fileName = "user_work_data_store",
            serializer = WorkUserSerializer
        )
    }
}