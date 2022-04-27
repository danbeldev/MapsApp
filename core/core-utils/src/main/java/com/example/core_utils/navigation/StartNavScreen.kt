package com.example.core_utils.navigation

const val START_ROUTE = "start_route"

sealed class StartNavScreen(val route:String) {
    object StartInfo:StartNavScreen("start_info_screen")
}