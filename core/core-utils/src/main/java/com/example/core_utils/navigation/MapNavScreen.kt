package com.example.core_utils.navigation

const val MAP_ROUE = "map_route"

sealed class MapNavScreen(val route:String) {
    object Map:MapNavScreen("map_screen")
}