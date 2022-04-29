package com.example.feature_map.view

import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun TransportRouteDialogView(
    value: MutableState<Boolean>,
    transport:MutableState<String>
) {
    if (value.value){
        var transportTab by remember { mutableStateOf(0) }
        AlertDialog(
            shape = AbsoluteRoundedCornerShape(15.dp),
            backgroundColor = Color.White,
            onDismissRequest = { value.value = false },
            buttons = {
                TabTransportSelectView(
                    selectedTabIndex = transportTab,
                    onClick = {
                        transport.value = it.title
                        transportTab = it.ordinal
                    }
                )
            }
        )
    }
}