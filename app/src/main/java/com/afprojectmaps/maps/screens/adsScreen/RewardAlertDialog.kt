package com.afprojectmaps.maps.screens.adsScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.afprojectmaps.maps.data.user.model.userSumMoneyVersion2
import com.afprojectmaps.maps.data.utils.model.Utils
import com.afprojectmaps.maps.ui.theme.primaryBackground
import com.afprojectmaps.maps.ui.theme.primaryText
import com.afprojectmaps.maps.ui.theme.secondaryBackground
import com.afprojectmaps.maps.ui.theme.tintColor

@Composable
fun RewardAlertDialog(
    utils: Utils?,
    countInterstitialAds:Int,
    countInterstitialAdsClick:Int,
    countRewardedAds:Int,
    countRewardedAdsClick:Int,
    achievementPrice: Double,
    referralLinkMoney: Double,
    giftMoney: Double,
    onDismissRequest:() -> Unit,
    onSendWithdrawalRequest: (phoneNumber: String) -> Unit
) {
    var phoneNumber by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("Введите свой номер телефона") }

    AlertDialog(
        modifier = Modifier
            .size(420.dp)
            .border(1.dp, tintColor, AbsoluteRoundedCornerShape(20.dp)),
        shape = AbsoluteRoundedCornerShape(20.dp),
        backgroundColor = primaryBackground,
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = "Деньги придут Вам в течение трех рабочих дней." +
                    "\nВывод на киви кошелёк." +
                    "\nМинимальная сумма для вывода ${utils?.min_price_withdrawal_request ?: ""} ₽",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(5.dp).fillMaxWidth(),
                color = primaryText
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(visible = errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W900,
                        color = Color.Red
                    )
                }

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    modifier = Modifier.padding(5.dp),
                    shape = AbsoluteRoundedCornerShape(15.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = primaryText,
                        disabledTextColor = tintColor,
                        backgroundColor = secondaryBackground,
                        cursorColor = tintColor,
                        focusedBorderColor = tintColor,
                        unfocusedBorderColor = secondaryBackground,
                        disabledBorderColor = secondaryBackground,
                        focusedLabelColor = tintColor,
                        unfocusedLabelColor = primaryText
                    ),
                    label = {
                        Text(text = "Номер телефона")
                    }
                )
            }
        },
        buttons = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                shape = AbsoluteRoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = tintColor
                ),
                onClick = {
                    if(utils != null){
                        verifySendWithdrawalRequest(
                            utils = utils,
                            giftMoney = giftMoney,
                            achievementPrice = achievementPrice,
                            countInterstitialAds = countInterstitialAds,
                            countInterstitialAdsClick = countInterstitialAdsClick,
                            countRewardedAds = countRewardedAds,
                            countRewardedAdsClick = countRewardedAdsClick,
                            referralLinkMoney = referralLinkMoney,
                            phoneNumber = phoneNumber.trim(),
                            onError = {
                                errorMessage = it
                            },
                            onSendWithdrawalRequest = {
                                errorMessage = ""
                                onSendWithdrawalRequest(phoneNumber)
                            }
                        )
                    }
                }
            ) {
                Text(text = "Отправить", color = primaryText)
            }
        }
    )
}

private fun verifySendWithdrawalRequest(
    utils: Utils,
    countInterstitialAds: Int,
    countInterstitialAdsClick: Int,
    countRewardedAds: Int,
    countRewardedAdsClick: Int,
    achievementPrice: Double,
    referralLinkMoney: Double,
    giftMoney: Double,
    phoneNumber: String,
    onSendWithdrawalRequest: (phoneNumber: String) -> Unit,
    onError: (String) -> Unit
){
    if(userSumMoneyVersion2(
            utils = utils,
            countInterstitialAds = countInterstitialAds,
            countInterstitialAdsClick = countInterstitialAdsClick,
            countRewardedAds = countRewardedAds,
            countRewardedAdsClick = countRewardedAdsClick,
            countBannerAds = 0,
            countBannerAdsClick = 0
        ) < utils.min_price_withdrawal_request){
        onError("Минимальная сумма для вывода ${utils.min_price_withdrawal_request} рублей")
    }else if(phoneNumber.isEmpty()){
        onError("Укажите номер телефона")
    }else {
        onSendWithdrawalRequest(phoneNumber)
    }
}