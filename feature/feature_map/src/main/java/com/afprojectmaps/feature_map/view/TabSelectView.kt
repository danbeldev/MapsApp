package com.afprojectmaps.feature_map.view

import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
internal fun TabTransportSelectView(
    selectedTabIndex:Int, onClick:(TabTransportEnum)-> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        TabTransportEnum.values().forEachIndexed { index, tabTransportEnum -> 
            Tab(
                selected = index == selectedTabIndex,
                onClick = { onClick(tabTransportEnum) },
                text = {
                    Text(text = tabTransportEnum.title)
                }
            )
        }
    }
}

enum class TabTransportEnum(val title:String){
    DRIVING_CAR(title = "driving-car"),
    DRIVING_HVG(title = "driving-hgv"),
    CYCLING_REGULAR(title = "cycling-regular"),
    CYCLING_ROAD(title = "cycling-road"),
    CYCLING_MOUNTAIN(title = "cycling-mountain"),
    CYCLING_ELECTRIC(title = "cycling-electric"),
    FOOT_WALKING(title = "foot-walking"),
    FOOT_HIKING(title = "foot-hiking"),
    WHEELCHAIR(title = "wheelchair")
}