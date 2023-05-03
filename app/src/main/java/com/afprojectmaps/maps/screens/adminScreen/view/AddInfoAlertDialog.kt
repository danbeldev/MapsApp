package com.afprojectmaps.maps.screens.adminScreen.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.afprojectmaps.maps.ui.theme.tintColor

@Composable
fun AddInfoAlertDialog(
    info: String,
    onDismissRequest: () -> Unit,
    onUpdateInfo: (String) -> Unit
) {
    var infoMutable by remember { mutableStateOf("") }
    val focusRequester = remember(::FocusRequester)

    LaunchedEffect(key1 = Unit, block = {
        infoMutable = info
        focusRequester.requestFocus()
    })

    AlertDialog(
        shape = AbsoluteRoundedCornerShape(20.dp),
        onDismissRequest = onDismissRequest,
        modifier = Modifier.border(1.dp, tintColor, AbsoluteRoundedCornerShape(20.dp)),
        title = {
            OutlinedTextField(
                value = infoMutable,
                onValueChange = { infoMutable = it },
                modifier = Modifier
                    .padding(5.dp)
                    .height(200.dp)
                    .focusRequester(focusRequester)
            )
        },
        buttons = {
            Button(
                modifier = Modifier.padding(15.dp).fillMaxWidth(),
                onClick = { onUpdateInfo(infoMutable) },
                shape = AbsoluteRoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = tintColor
                )
            ) {
                Text(text = "Сохранить")
            }
        }
    )
}