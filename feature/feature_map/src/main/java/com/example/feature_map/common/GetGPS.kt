package com.example.feature_map.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationManager


@SuppressLint("MissingPermission")
internal fun getGPS(
    context: Context
): String {
    val lm = context.getSystemService(LOCATION_SERVICE) as LocationManager
    val providers = lm.getProviders(true)
    var l: Location? = null
    for (i in providers.indices.reversed()) {
        l = lm.getLastKnownLocation(providers[i])
        if (l != null) break
    }
    val gps = DoubleArray(2)
    if (l != null) {
        gps[0] = l.latitude
        gps[1] = l.longitude
    }
    return "${gps[1]},${gps[0]}"
}