package com.afprojectmaps.maps.screens.adsScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.afnotesproject.notes.yandexAds.InterstitialYandexAds
import com.afnotesproject.notes.yandexAds.RewardedYandexAds
import com.afprojectmaps.maps.R
import com.afprojectmaps.maps.common.openBrowser
import com.afprojectmaps.maps.common.vpn
import com.afprojectmaps.maps.data.user.UserDataStore
import com.afprojectmaps.maps.data.user.model.User
import com.afprojectmaps.maps.data.user.model.UserRole
import com.afprojectmaps.maps.data.user.model.userSumMoneyVersion2
import com.afprojectmaps.maps.data.utils.UtilsDataStore
import com.afprojectmaps.maps.data.utils.model.Utils
import com.afprojectmaps.maps.data.withdrawalRequest.WithdrawalRequestDataStore
import com.afprojectmaps.maps.data.withdrawalRequest.model.WithdrawalRequest
import com.afprojectmaps.maps.ui.theme.primaryBackground
import com.afprojectmaps.maps.ui.theme.primaryText
import com.afprojectmaps.maps.ui.theme.tintColor
import org.joda.time.Period

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AdsScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val screenHeightDp = LocalConfiguration.current.screenHeightDp

    val userDataStore = remember(::UserDataStore)
    val utilsDataStore = remember(::UtilsDataStore)
    val withdrawalRequestDataStore = remember(::WithdrawalRequestDataStore)
    var user by remember { mutableStateOf<User?>(null) }
    var utils by remember { mutableStateOf<Utils?>(null) }
    var rewardAlertDialog by remember { mutableStateOf(false) }

    val interstitialYandexAds = remember {
        InterstitialYandexAds(context, onDismissed = { adClickedDate, returnedToDate ->
            user ?: return@InterstitialYandexAds

            if(adClickedDate != null && returnedToDate != null){
                if((Period(adClickedDate, returnedToDate)).seconds >= 10){
                    userDataStore.updateCountInterstitialAdsClick(user!!.countInterstitialAdsClick + 1)
                }else {
                    userDataStore.updateCountInterstitialAds(user!!.countInterstitialAds + 1)
                }
            } else {
                userDataStore.updateCountInterstitialAds(user!!.countInterstitialAds + 1)
            }
        })
    }

    val rewardedYandexAds = remember {
        RewardedYandexAds(context, onDismissed = { adClickedDate, returnedToDate, rewarded ->
            if(adClickedDate != null && returnedToDate != null && rewarded && user != null){
                if((Period(adClickedDate, returnedToDate)).seconds >= 10){
                    userDataStore.updateCountRewardedAdsClick(user!!.countRewardedAdsClick + 1)
                }else {
                    userDataStore.updateCountRewardedAds(user!!.countRewardedAds + 1)
                }
            } else if(rewarded && user != null){
                userDataStore.updateCountRewardedAds(user!!.countRewardedAds + 1)
            }
        })
    }

    if(rewardAlertDialog) {
        RewardAlertDialog(
            utils = utils,
            referralLinkMoney = user?.referralLinkMoney ?: 0.0,
            giftMoney = user?.giftMoney ?: 0.0,
            countInterstitialAds = user?.countInterstitialAds ?: 0,
            countInterstitialAdsClick = user?.countInterstitialAdsClick ?: 0,
            countRewardedAds = user?.countRewardedAds ?: 0,
            countRewardedAdsClick = user?.countRewardedAdsClick ?: 0,
            achievementPrice = user?.achievementPrice ?: 0.0,
            onDismissRequest = { rewardAlertDialog = false },
            onSendWithdrawalRequest = { phoneNumber ->
                user ?: return@RewardAlertDialog
                utils ?: return@RewardAlertDialog

                val withdrawalRequest = WithdrawalRequest(
                    countInterstitialAds = user!!.countInterstitialAds,
                    countInterstitialAdsClick = user!!.countInterstitialAdsClick,
                    countRewardedAds = user!!.countRewardedAds,
                    countRewardedAdsClick = user!!.countRewardedAdsClick,
                    phoneNumber = phoneNumber,
                    userEmail = user!!.email,
                    version = 1,
                    achievementPrice = user!!.achievementPrice,
                    referralLinkMoney = user!!.referralLinkMoney,
                    giftMoney = user!!.giftMoney,
                    vpn = vpn()
                )

                withdrawalRequestDataStore.create(
                    utils = utils!!,
                    withdrawalRequest = withdrawalRequest
                ) {
                    if (it.isSuccessful) {
                        rewardAlertDialog = false
                        Toast.makeText(context, "Заявка отправлена", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Ошибка: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }

    LaunchedEffect(key1 = Unit, block = {
        userDataStore.get { user = it }
        utilsDataStore.get({ utils = it })
    })

    BackdropScaffold(
        appBar = {  },
        scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed),
        backLayerBackgroundColor = tintColor,
        frontLayerBackgroundColor = primaryBackground,
        frontLayerShape = AbsoluteRoundedCornerShape(30.dp),
        peekHeight = 200.dp,
        gesturesEnabled = false,
        backLayerContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ads),
                    contentDescription = null,
                    modifier = Modifier
                        .size(170.dp)
                        .padding(20.dp)
                )
            }
        },
        frontLayerContent = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    text = "Смотрите рекламу и зарабатывайте !",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W900,
                    modifier = Modifier.padding(15.dp),
                    fontSize = 20.sp,
                    color = primaryText
                )

                Board(
                    modifier = Modifier.padding(10.dp),
                    text = userSumMoneyVersion2(
                        utils = utils,
                        countInterstitialAds = user?.countInterstitialAds ?: 0,
                        countInterstitialAdsClick = user?.countInterstitialAdsClick ?: 0,
                        countRewardedAds = user?.countRewardedAds ?: 0,
                        countRewardedAdsClick = user?.countRewardedAdsClick ?: 0,
                        countBannerAds = user?.countBannerAds ?: 0,
                        countBannerAdsClick = user?.countBannerAdsClick ?: 0,
                    ).toString(),
                    width = (screenWidthDp / 2).toDouble(),
                    height = (screenHeightDp / 10).toDouble()
                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    BaseButton(
                        text = "Видео реклама",
                        onClick = {
                            rewardedYandexAds.show()
                        }
                    )

                    BaseButton(
                        text = "Полноэкранная реклама",
                        onClick = {
                            interstitialYandexAds.show()
                        }
                    )

                    BaseButton(
                        text = "Вывести",
                        onClick = {
                            rewardAlertDialog = true
                        }
                    )

                    if(user?.userRole == UserRole.ADMIN){
                        BaseButton(
                            text = "Админ",
                            onClick = {
                                navController.navigate("AdminScreen")
                            }
                        )
                    }

                    IconButton(onClick = {
                        context.openBrowser("https://t.me/MapsApppril")
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.telegram),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .padding(5.dp)
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun Board(modifier: Modifier, text: String, width: Double, height: Double) {
    Text(
        text = "$text ₽",
        fontSize = 24.sp,
        fontWeight = FontWeight.W900,
        modifier = Modifier.padding(10.dp),
        color = primaryText
    )
}

@Composable
fun BaseButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .height(70.dp)
            .width(250.dp)
            .padding(5.dp),
        shape = AbsoluteRoundedCornerShape(25.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = tintColor
        ),
        onClick = onClick
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = primaryText
        )
    }
}