package com.example.feature_weather

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.example.core_network_domain.common.Response
import com.example.core_network_domain.entities.infoMap.SearchResult
import com.example.core_network_domain.entities.weather.WeatherAlert
import com.example.core_network_domain.entities.weather.WeatherResult
import com.example.core_utils.navigation.MapNavScreen
import com.example.feature_weather.common.getGPS
import com.example.feature_weather.viewModel.WeatherViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalPermissionsApi
@Composable
fun WeatherScreen(
    navController: NavController,
    weatherViewModel: WeatherViewModel
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope

    val permission = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)

    val animationError = rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.error))
    val animationLoading = rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading))

    val progressAnimationError = animateLottieCompositionAsState(
        composition = animationError.value,
        iterations = LottieConstants.IterateForever
    )

    val progressAnimationLoading = animateLottieCompositionAsState(
        composition = animationLoading.value,
        iterations = LottieConstants.IterateForever
    )

    LaunchedEffect(key1 = Unit, block = {
        permission.launchPermissionRequest()
    })

    if (permission.hasPermission){
        val backdropState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed)
        var weather:Response<WeatherResult> by remember { mutableStateOf(Response.Loading()) }
        var reverse by remember { mutableStateOf(SearchResult()) }
        var weatherAlerts by remember { mutableStateOf(WeatherAlert()) }
        var weatherDailyHourly by remember { mutableStateOf(WeatherAlert()) }

        weatherViewModel.getWeather(
            lat = getGPS(context)[0],
            lon = getGPS(context)[1]
        )

        weatherViewModel.getReverse(
            lat = getGPS(context)[0].toString(),
            lon = getGPS(context)[1].toString()
        )

        weatherViewModel.getWeatherAlerts(
            lat = getGPS(context)[0],
            lon = getGPS(context)[1]
        )

        weatherViewModel.getWeatherDailyHourly(
            lat = getGPS(context)[0],
            lon = getGPS(context)[1]
        )

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                weatherViewModel.responseWeather.onEach {
                    weather = it
                }.collect()
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                weatherViewModel.responseReverse.onEach {
                    reverse = it
                }.collect()
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                weatherViewModel.responseWeatherAlerts.onEach {
                    weatherAlerts = it
                }.collect()
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                weatherViewModel.responseWeatherDailyHourly.onEach {
                    weatherDailyHourly = it
                }.collect()
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            when(weather){
                is Response.Error -> {
                    LottieAnimation(
                        modifier = Modifier.fillMaxSize(),
                        composition = animationError.value,
                        progress = progressAnimationError.progress
                    )
                }
                is Response.Loading -> {
                    LottieAnimation(
                        modifier = Modifier.fillMaxSize(),
                        composition = animationLoading.value,
                        progress = progressAnimationLoading.progress
                    )
                }
                is Response.Success -> {
                    val item = weather.data!!

                    BackdropScaffold(
                        scaffoldState = backdropState,
                        appBar = { /*TODO*/ },
                        peekHeight = 400.dp,
                        headerHeight = 0.dp,
                        gesturesEnabled = false,
                        backLayerBackgroundColor = Color.Transparent,
                        backLayerContent = {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Brush.linearGradient(
                                            listOf(
                                                Color(0xFF47BFDF),
                                                Color(0xFF4A91FF)
                                            )
                                        )
                                    )
                            ) {
                                LazyColumn(content = {
                                    item {
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Spacer(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(200.dp)
                                            )

                                            Text(
                                                text = "${item.main.temp} °",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 64.sp,
                                                modifier = Modifier.padding(5.dp),
                                                color = Color.White
                                            )

                                            LazyRow(content = {
                                                items(item.weather){ weather ->
                                                    Text(
                                                        text = weather.description,
                                                        modifier = Modifier.padding(5.dp),
                                                        color = Color.White,
                                                        fontSize = 20.sp
                                                    )
                                                }
                                            })

                                            Spacer(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(100.dp)
                                            )
                                        }
                                    }
                                })
                            }
                        },
                        frontLayerContent = {

                            Column {
                                Text(
                                    text = "Today",
                                    modifier = Modifier.padding(
                                        top = 20.dp,
                                        start = 20.dp,
                                        bottom = 10.dp
                                    ),
                                    color = Color(0xFF6B6969),
                                    fontSize = 24.sp
                                )

                                LazyRow(content = {
                                    items(weatherDailyHourly.hourly){ item ->

                                        Card(
                                            shape = AbsoluteRoundedCornerShape(10.dp),
                                            modifier = Modifier
                                                .padding(5.dp),
                                            elevation = 10.dp
                                        ) {
                                            Column {

                                                Text(
                                                    text = "${item.temp} °",
                                                    modifier = Modifier.padding(5.dp),
                                                    fontSize = 12.sp
                                                )

                                                Text(
                                                    text = item.weather[0].description,
                                                    modifier = Modifier
                                                        .padding(5.dp)
                                                )
                                            }
                                        }
                                    }
                                })


                                Text(
                                    text = "Next 8 Days",
                                    modifier = Modifier.padding(
                                        top = 20.dp,
                                        start = 20.dp,
                                        bottom = 10.dp
                                    ),
                                    color = Color(0xFF6B6969),
                                    fontSize = 24.sp
                                )

                                LazyRow(content = {
                                    itemsIndexed(weatherAlerts.daily){ index, item ->

                                        Card(
                                            shape = AbsoluteRoundedCornerShape(10.dp),
                                            modifier = Modifier
                                                .padding(5.dp),
                                            elevation = 10.dp
                                        ) {
                                            Column {

                                                Text(
                                                    text = "${item.temp.day} °",
                                                    modifier = Modifier.padding(5.dp),
                                                    fontSize = 12.sp
                                                )

                                                Text(
                                                    text = when(index){
                                                        0 -> "Monday"
                                                        1 -> "Tuesday"
                                                        2 -> "Wednesday"
                                                        3 -> "Thursday"
                                                        4 -> "Friday"
                                                        5 -> "Saturday"
                                                        6 -> "Sunday"
                                                        else -> "Monday"
                                                    },
                                                    modifier = Modifier
                                                        .padding(5.dp)
                                                        .width(100.dp)
                                                )

                                                Text(
                                                    text = item.weather[0].description,
                                                    modifier = Modifier
                                                        .padding(5.dp)
                                                )
                                            }
                                        }
                                    }
                                })
                            }
                        }
                    )
                }
            }
        }
    }else{
        navController.navigate(MapNavScreen.Map.route){
            popUpTo(MapNavScreen.Map.route){
                inclusive = true
            }
        }
    }
}