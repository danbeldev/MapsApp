package com.afprojectmaps.maps.ui.activity

import android.app.Application
import com.yandex.mobile.ads.common.MobileAds

class AppApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this){  }
    }
}