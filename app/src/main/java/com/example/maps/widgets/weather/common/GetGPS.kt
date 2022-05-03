package com.example.maps.widgets.weather.common

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager

@SuppressLint("MissingPermission")
internal fun getGPS(
    context: Context
): DoubleArray {
    val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
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
    return gps
}