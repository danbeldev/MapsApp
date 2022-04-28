package com.example.feature_weather

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

        var weather:Response<WeatherResult> by remember { mutableStateOf(Response.Loading()) }
        var reverse by remember { mutableStateOf(SearchResult()) }
        var weatherAlerts by remember { mutableStateOf(WeatherAlert()) }

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
                                        .height(30.dp)
                                )

                                Text(
                                    text = reverse.display_name,
                                    modifier = Modifier.padding(5.dp)
                                )

                                Card(
                                    modifier = Modifier.fillMaxSize(),
                                    shape = AbsoluteRoundedCornerShape(10.dp),
                                    backgroundColor = Color.White,
                                    elevation = 15.dp
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column {
                                            Text(text = "TEMPERATURE")
                                        }
                                    }
                                }

                                Text(
                                    text = "${item.main.temp} C",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp,
                                    modifier = Modifier.padding(5.dp)
                                )

                                LazyRow(content = {
                                    items(item.weather){ weather ->
                                        Text(
                                            text = weather.description,
                                            modifier = Modifier.padding(5.dp)
                                        )
                                    }
                                })
                            }
                        }

                        itemsIndexed(weatherAlerts.daily){ index, item ->
                            Column {
                                
                                Text(text = item.weather.toString())
                                
                                Divider()
                            }
                        }
                    })
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