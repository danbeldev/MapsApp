package com.afprojectmaps.maps.screens.adminScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.afprojectmaps.maps.data.utils.UtilsDataStore
import com.afprojectmaps.maps.data.utils.model.Utils
import com.afprojectmaps.maps.data.withdrawalRequest.model.WithdrawalRequestStatus
import com.afprojectmaps.maps.screens.adminScreen.view.AddInfoAlertDialog
import com.afprojectmaps.maps.ui.theme.primaryBackground
import com.afprojectmaps.maps.ui.theme.primaryText
import com.afprojectmaps.maps.ui.theme.tintColor

@Composable
fun AdminScreen(
    navController: NavController
) {
    val utilsDataStore = remember(::UtilsDataStore)
    var utils by remember { mutableStateOf<Utils?>(null) }
    var addInfoAlertDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit, block = {
        utilsDataStore.get({utils = it})
    })

    if(addInfoAlertDialog){
        AddInfoAlertDialog(
            info = utils?.info ?: "",
            onDismissRequest = { addInfoAlertDialog = false },
            onUpdateInfo = {
                utilsDataStore.updateInfo(it){
                    addInfoAlertDialog = false
                    utilsDataStore.get({utils = it})
                }
            }
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBackground
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Button(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                shape = AbsoluteRoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = tintColor
                ),
                onClick = {
                    navController.navigate(
                        "WithdrawalRequestsScreen/${WithdrawalRequestStatus.PAID}"
                    )
                }
            ) {
                Text(
                    text = "Заявки статус '${WithdrawalRequestStatus.PAID.text}'",
                    color = primaryText
                )
            }

            Button(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                shape = AbsoluteRoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = tintColor
                ),
                onClick = {
                    navController.navigate(
                        "WithdrawalRequestsScreen/${WithdrawalRequestStatus.WAITING}"
                    )
                }
            ) {
                Text(
                    text = "Заявки статус '${WithdrawalRequestStatus.WAITING.text}'",
                    color = primaryText
                )
            }

            Button(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                shape = AbsoluteRoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = tintColor
                ),
                onClick = {
                    navController.navigate("SettingsScreen")
                }
            ) {
                Text(text = "Настройки", color = primaryText)
            }
        }
    }
}