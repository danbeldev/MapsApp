package com.example.feature_map

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.*
import com.example.core_network_domain.common.Response
import com.example.core_network_domain.entities.infoMap.InfoMarker
import com.example.core_network_domain.entities.infoMap.SearchResult
import com.example.core_utils.style_map.retro
import com.example.feature_map.viewModel.MapViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@Composable
fun MapScreen(
    navController: NavController,
    mapViewModel: MapViewModel
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    var search by remember { mutableStateOf("") }
    var searchResult:Response<List<SearchResult>> by
        remember { mutableStateOf(Response.Loading()) }

    var searchLatLng:LatLng? by remember { mutableStateOf(null) }

    val backdropState = rememberBackdropScaffoldState(initialValue = BackdropValue.Revealed)
    val permission = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)

    val animationError = rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.error))
    val animationLoading = rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading))
    val animationNullable = rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.nullable))

    val progressAnimationError = animateLottieCompositionAsState(
        composition = animationError.value,
        iterations = LottieConstants.IterateForever
    )

    val progressAnimationLoading = animateLottieCompositionAsState(
        composition = animationLoading.value,
        iterations = LottieConstants.IterateForever
    )

    val progressAnimationNullable = animateLottieCompositionAsState(
        composition = animationLoading.value,
        iterations = LottieConstants.IterateForever
    )

    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            mapViewModel.responseSearch.onEach {
                searchResult = it
            }.collect()
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        permission.launchPermissionRequest()
    })

    LaunchedEffect(key1 = search, block = {
        if (search.isNotEmpty()){
            mapViewModel.getSearch(
                city = search,
                country = "",
                county = "",
                postalcode = ""
            )
        }
    })

    BackdropScaffold(
        scaffoldState = backdropState,
        peekHeight = 300.dp,
        backLayerBackgroundColor = Color.Gray,
        appBar = {},
        backLayerContent = {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(searchLatLng ?: LatLng(0.1,0.1),5f)
                },
                properties = MapProperties(
                    isMyLocationEnabled = permission.hasPermission,
                    mapStyleOptions = MapStyleOptions(retro)
                )
            )
        },
        frontLayerContent = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier.padding(5.dp),
                    value = search,
                    onValueChange = { search = it },
                    label = { Text(text = "Search") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.Black,
                        disabledTextColor = Color.Black,
                        backgroundColor = Color.White,
                        cursorColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    )
                )


                when(searchResult){
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
                        if (searchResult.data!!.isEmpty()){
                            LottieAnimation(
                                modifier = Modifier.fillMaxSize(),
                                composition = animationNullable.value,
                                progress = progressAnimationNullable.progress
                            )
                        }else{
                            LazyColumn(content = {
                                items(searchResult.data!!){ item ->

                                    var infoMarker by remember { mutableStateOf(listOf<InfoMarker>()) }

                                    lifecycleScope.launch {
                                        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                                            mapViewModel.gerInfoMarker(
                                                osmIds = item.osm_id
                                            ).onEach {
                                                infoMarker = it
                                            }.collect()
                                        }
                                    }

                                    Column(
                                        modifier = Modifier.pointerInput(Unit){
                                            detectTapGestures(onTap = {
                                                searchLatLng = LatLng(
                                                    item.lat.toDouble(),
                                                    item.lot.toDouble()
                                                )
                                            })
                                        }
                                    ) {
                                        Text(
                                            text = item.display_name,
                                            modifier = Modifier
                                                .padding(5.dp)
                                        )

                                        LazyRow(content = {
                                            items(infoMarker){ item ->
                                                item.extratags.image?.let {
                                                    Image(
                                                        painter = rememberImagePainter(data = item.extratags.image),
                                                        contentDescription = null,
                                                        modifier = Modifier.size(150.dp)
                                                    )
                                                }
                                            }
                                        })
                                        Divider()
                                    }
                                }
                            })
                        }
                    }
                }
            }
        }
    )
}