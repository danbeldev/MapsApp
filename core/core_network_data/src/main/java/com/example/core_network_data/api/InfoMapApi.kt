package com.example.core_network_data.api

import com.example.core_network_data.common.ConstantsUrl.INFO_MARKER_URL
import com.example.core_network_data.common.ConstantsUrl.REVERSE_URL
import com.example.core_network_data.common.ConstantsUrl.SEARCH_URL
import com.example.core_network_domain.entitier.infoMap.InfoMarker
import com.example.core_network_domain.entitier.infoMap.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface InfoMapApi {

    @GET(SEARCH_URL)
    suspend fun getSearch(
        @Query("format") format:String = "json",
        @Query("city") city:String,
        @Query("county") county:String,
        @Query("state") state:String,
        @Query("country") country:String,
        @Query("postalcode") postalcode:String
    ):Response<List<SearchResult>>

    @GET(INFO_MARKER_URL)
    suspend fun getInfoMarker(
        @Query("format") format:String = "json",
        @Query("extratags") extratags:String = "1",
        @Query("osm_ids") osmIds:String
    ):Response<List<InfoMarker>>

    @GET(REVERSE_URL)
    suspend fun getReverse(
        @Query("format") format:String = "json",
        @Query("lat") lat:String,
        @Query("lon") lon:String,
        @Query("zoom") zoom:Int = 18,
        @Query("addressdetails") addressdetails:Int = 1
    ):Response<SearchResult>
}