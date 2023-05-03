package com.afnotesproject.notes.screens.withdrawalRequestsScreen

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.afprojectmaps.maps.common.setClipboard
import com.afprojectmaps.maps.data.user.model.userSumMoneyVersion2
import com.afprojectmaps.maps.data.utils.UtilsDataStore
import com.afprojectmaps.maps.data.utils.model.Utils
import com.afprojectmaps.maps.data.withdrawalRequest.WithdrawalRequestDataStore
import com.afprojectmaps.maps.data.withdrawalRequest.model.WithdrawalRequest
import com.afprojectmaps.maps.data.withdrawalRequest.model.WithdrawalRequestStatus
import com.afprojectmaps.maps.ui.theme.primaryBackground
import com.afprojectmaps.maps.ui.theme.primaryText
import com.afprojectmaps.maps.ui.theme.secondaryBackground
import com.afprojectmaps.maps.ui.theme.tintColor
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WithdrawalRequestsScreen(
    navController: NavController,
    withdrawalRequestStatus: WithdrawalRequestStatus
) {
    val context = LocalContext.current

    var deleteWithdrawalRequestId by remember { mutableStateOf("") }
    var utils by remember { mutableStateOf<Utils?>(null) }
    var withdrawalRequests by remember { mutableStateOf(listOf<WithdrawalRequest>()) }
    val withdrawalRequestDataStore = remember(::WithdrawalRequestDataStore)
    val utilsDataStore = remember(::UtilsDataStore)

    LaunchedEffect(key1 = Unit, block = {
        withdrawalRequestDataStore.getAll({
            withdrawalRequests = it.filter {
                withdrawalRequestStatus == it.status
            }
        }, {})
        utilsDataStore.get({utils = it})
    })

    if (deleteWithdrawalRequestId.isNotEmpty()) {
        DeleteWithdrawalRequestAlertDialog(
            onDismissRequest = {
                deleteWithdrawalRequestId = ""
            },
            confirm = {
                withdrawalRequestDataStore.updateStatus(
                    id = deleteWithdrawalRequestId,
                    status = when(withdrawalRequestStatus){
                        WithdrawalRequestStatus.WAITING -> WithdrawalRequestStatus.PAID
                        WithdrawalRequestStatus.PAID -> WithdrawalRequestStatus.WAITING
                    },
                    onSuccess = {
                        deleteWithdrawalRequestId = ""

                        withdrawalRequestDataStore.getAll({
                            withdrawalRequests = it.filter {
                                withdrawalRequestStatus == it.status
                            }
                        }, {})
                    },
                    onError = {
                        Toast.makeText(context, "Ошибка: $it", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBackground
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(withdrawalRequests) { item ->

                Card(
                    shape = AbsoluteRoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    backgroundColor = secondaryBackground
                ) {
                    Column {
                        Text(
                            text = "Индификатор пользователя : ${item.userId}",
                            modifier = Modifier
                                .padding(5.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(onLongPress = {
                                        setClipboard(context, item.userId)
                                    })
                                },
                            color = primaryText
                        )

                        Text(
                            text = "Электронная почта : ${item.userEmail}",
                            modifier = Modifier
                                .padding(5.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(onLongPress = {
                                        setClipboard(context, item.userEmail)
                                    })
                                },
                            color = primaryText
                        )

                        Text(
                            text = "Номер телефона : ${item.phoneNumber}",
                            modifier = Modifier
                                .padding(5.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(onLongPress = {
                                        setClipboard(context, item.phoneNumber)
                                    })
                                },
                            color = primaryText
                        )

                        Text(
                            text = "Полноэкраная : ${item.countInterstitialAds}",
                            modifier = Modifier
                                .padding(5.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(onLongPress = {
                                        setClipboard(context, (item.countInterstitialAds.toString()))
                                    })
                                },
                            color = primaryText
                        )

                        Text(
                            text = "Полноэкраная переход 10 сек : ${item.countInterstitialAdsClick}",
                            modifier = Modifier
                                .padding(5.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(onLongPress = {
                                        setClipboard(context, item.countInterstitialAdsClick.toString())
                                    })
                                },
                            color = primaryText
                        )

                        Text(
                            text = "Вознаграждением : ${item.countRewardedAds}",
                            modifier = Modifier
                                .padding(5.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(onLongPress = {
                                        setClipboard(context, item.countRewardedAds.toString())
                                    })
                                },
                            color = primaryText
                        )

                        Text(
                            text = "Вознаграждением переход на 10 сек: ${item.countRewardedAdsClick}",
                            modifier = Modifier
                                .padding(5.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(onLongPress = {
                                        setClipboard(context, item.countRewardedAdsClick.toString())
                                    })
                                },
                            color = primaryText
                        )

                        Text(
                            text = "Vpn: ${if(item.vpn) "Да" else "Нет"}",
                            modifier = Modifier
                                .padding(5.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(onLongPress = {
                                        setClipboard(context, item.achievementPrice.toString())
                                    })
                                },
                            color = primaryText
                        )

                        utils?.let {

                            val sum = userSumMoneyVersion2(
                                utils = it,
                                countInterstitialAds = item.countInterstitialAds,
                                countInterstitialAdsClick = item.countInterstitialAdsClick,
                                countRewardedAds = item.countRewardedAds,
                                countRewardedAdsClick = item.countRewardedAdsClick,
                                countBannerAds = 0,
                                countBannerAdsClick = 0
                            )

                            Text(
                                text = "Сумма для ввывода : $sum",
                                modifier = Modifier
                                    .padding(5.dp)
                                    .pointerInput(Unit) {
                                        detectTapGestures(onLongPress = {
                                            setClipboard(context, sum.toString())
                                        })
                                    },
                                color = primaryText
                            )
                        }

                        item.date?.let { date ->
                            Text(
                                text = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(Date(date)),
                                color = tintColor,
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth()
                                    .pointerInput(Unit) {
                                        detectTapGestures(onLongPress = {
                                            setClipboard(context, item.achievementPrice.toString())
                                        })
                                    }
                            )
                        }

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            onClick = { deleteWithdrawalRequestId = item.id },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = tintColor
                            )
                        ) {
                            Text(text = "Сменить статус", color = primaryText)
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun DeleteWithdrawalRequestAlertDialog(
    onDismissRequest: () -> Unit,
    confirm: () -> Unit
) {
    AlertDialog(
        shape = AbsoluteRoundedCornerShape(15.dp),
        onDismissRequest = onDismissRequest,
        buttons = {
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                onClick = confirm
            ) {
                Text(text = "Подтвердить", color = Color.Red)
            }
        }
    )
}