package com.afprojectmaps.maps.screens.authScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.afprojectmaps.maps.R
import com.afprojectmaps.maps.data.auth.AuthDataStore
import com.afnotesproject.notes.yandexAds.InterstitialYandexAds
import kotlinx.coroutines.delay
import com.afprojectmaps.maps.screens.adsScreen.BaseButton
import com.afprojectmaps.maps.ui.theme.primaryBackground
import com.afprojectmaps.maps.ui.theme.primaryText
import com.afprojectmaps.maps.ui.theme.secondaryBackground
import com.afprojectmaps.maps.ui.theme.tintColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AuthScreen(
    navController: NavController
) {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    val authDataStore = remember(::AuthDataStore)
    val interstitialYandexAds = remember {
        InterstitialYandexAds(context, onDismissed = { _, _ -> })
    }

    LaunchedEffect(key1 = Unit, block = {
        delay(3000L)
        interstitialYandexAds.show()
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
                    painter = painterResource(id = R.drawable.login),
                    contentDescription = null,
                    modifier = Modifier
                        .size(170.dp)
                        .padding(20.dp)
                )
            }
        },
        frontLayerContent = {
            Column {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        Spacer(modifier = Modifier.height(50.dp))

                        Text(
                            text = "Добро пожаловать",
                            modifier = Modifier.padding(10.dp),
                            fontSize = 25.sp,
                            fontWeight = FontWeight.W900,
                            color = primaryText
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(50.dp))

                        Text(
                            text = error,
                            color = Color.Red,
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth(),
                            fontWeight = FontWeight.W900,
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp
                        )

                        OutlinedTextField(
                            modifier = Modifier.padding(5.dp),
                            value = email,
                            onValueChange = { email = it },
                            shape = AbsoluteRoundedCornerShape(15.dp),
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
                                Text(text = "Электронная почта")
                            }
                        )

                        OutlinedTextField(
                            modifier = Modifier.padding(5.dp),
                            value = password,
                            onValueChange = { password = it },
                            shape = AbsoluteRoundedCornerShape(15.dp),
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
                                Text(text = "Пароль")
                            }
                        )

                        BaseButton(
                            text = "Войти в систему",
                            onClick = {
                                try {
                                    authDataStore.signIn(email.trim(),password.trim(),{
                                        navController.navigate("AdsScreen")
                                    },{
                                        error = it
                                    })
                                }catch(e: IllegalArgumentException){
                                    error = "Заполните все поля"
                                }catch (e:Exception){
                                    error = "Ошибка"
                                }
                            }
                        )

                        BaseButton(
                            text = "Регистрация",
                            onClick = {
                                try {
                                    authDataStore.registration(email.trim(),password.trim(),{
                                        navController.navigate("AdsScreen")
                                    },{
                                        error = it
                                    })
                                }catch(e: IllegalArgumentException){
                                    error = "Заполните все поля"
                                }catch (e:Exception){
                                    error = "Ошибка"
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}