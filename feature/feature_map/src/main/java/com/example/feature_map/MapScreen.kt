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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
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
import com.example.core_network_domain.entities.route.Route
import com.example.core_utils.navigation.WeatherNavScreen
import com.example.core_utils.style_map.retro
import com.example.feature_map.common.getGPS
import com.example.feature_map.state.SearchState
import com.example.feature_map.view.MarkerClickDialogView
import com.example.feature_map.view.TransportRouteDialogView
import com.example.feature_map.viewModel.MapViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
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
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    var search by remember { mutableStateOf("") }
    var route:Route? by remember { mutableStateOf(null) }
    var searchResults:Response<List<SearchResult>> by
        remember { mutableStateOf(Response.Loading()) }

    val markerClickDialog = remember { mutableStateOf(false) }
    val transportDialog = remember { mutableStateOf(false) }

    var searchResult:SearchResult? by remember { mutableStateOf(null) }

    val backdropState = rememberBackdropScaffoldState(initialValue = BackdropValue.Revealed)
    val permission = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)

    val animationError = rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.error))
    val animationLoading = rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading))
    val animationNullable = rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.nullable))

    var expandedDropdownMenuSearch by remember { mutableStateOf(false) }
    var searchState by remember { mutableStateOf(SearchState.CITY) }

    val transport = remember { mutableStateOf("driving-car") }

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

    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(getGPS(context = context), 17f)
    }

    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            mapViewModel.responseSearch.onEach {
                searchResults = it
            }.collect()
        }
    }

    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            mapViewModel.responseRoute.onEach {
                route = it
            }.collect()
        }
    }

    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            mapViewModel.responseReverse.onEach {
                searchResult = it
            }.collect()
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        permission.launchPermissionRequest()
    })

    LaunchedEffect(key1 = search,key2 = searchState ,block = {
        if (search.isNotEmpty()){
            mapViewModel.getSearch(
                city = if (searchState == SearchState.CITY) search else "",
                country = if (searchState == SearchState.COUNTRY) search else "",
                county = if (searchState == SearchState.COUNTY) search else "",
                postalcode = if (searchState == SearchState.POSTAL_CODE) search else "",
                street = if (searchState == SearchState.STREET) search else ""
            )
        }
    })

    LaunchedEffect(key1 = searchResult, block = {
        searchResult?.let {
            cameraPosition.position = CameraPosition.fromLatLngZoom(LatLng(it.lat.toDouble(), it.lon.toDouble()), 17f)
        }
    })

    LaunchedEffect(key1 = transport.value,key2 = searchResult, block = {
        if (transport.value.isNotEmpty()){
            mapViewModel.getRouteUseCase(
                profile = transport.value,
                start = "${getGPS(context).longitude},${getGPS(context).latitude}",
                end = "${searchResult?.lon},${searchResult?.lat}"
            )
        }
    })

    BackdropScaffold(
        scaffoldState = backdropState,
        peekHeight = 300.dp,
        backLayerBackgroundColor = Color.Gray,
        appBar = {},
        backLayerContent = {
            Box(modifier = Modifier.fillMaxSize()){
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPosition,
                    properties = MapProperties(
                        isMyLocationEnabled = permission.hasPermission,
                        mapStyleOptions = MapStyleOptions(retro)
                    ),
                    onMapClick = {
                        mapViewModel.getReverse(
                            lat = it.latitude.toString(),
                            lon = it.longitude.toString()
                        )

                    },
                    content = {
                        searchResults.data?.let { result ->
                            result.forEach { markerData ->
                                Marker(
                                    position = LatLng(markerData.lat.toDouble(), markerData.lon.toDouble()),
                                    title = markerData.display_name,
                                    onInfoWindowClick = {
                                        mapViewModel.getReverse(
                                            lat = it.position.latitude.toString(),
                                            lon = it.position.longitude.toString()
                                        )
                                        markerClickDialog.value = true
                                    }
                                )
                            }
                        }

                        searchResult?.let {

                            Marker(
                                title = searchResult!!.display_name,
                                position = LatLng(
                                    searchResult!!.lat.toDouble(), searchResult!!.lon.toDouble()
                                ),
                                onInfoWindowClick = {
                                    markerClickDialog.value = true
                                }
                            )
                        }

                        route?.let {
                            it.features.forEach { feature ->
                                val coordinates = ArrayList<LatLng>()
                                feature.geometry.coordinates.forEach {  coordinate ->
                                    coordinates.add(
                                        LatLng(coordinate[1], coordinate[0])
                                    )
                                }
                                Polyline(
                                    points = coordinates,
                                    color = Color.Red
                                )
                            }
                        }
                    }
                )
                IconButton(
                    modifier = Modifier.padding(10.dp),
                    onClick = {
                        navController.navigate(WeatherNavScreen.WeatherInfo.route)
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.weather),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

        },
        frontLayerContent = {

            MarkerClickDialogView(
                navController = navController,
                value = markerClickDialog,
                transportDialog = transportDialog,
                title = searchResult?.display_name.toString(),
                lat = searchResult?.lat.toString(),
                lon = searchResult?.lon.toString()
            )

            TransportRouteDialogView(
                value = transportDialog,
                transport = transport
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier.padding(5.dp),
                    value = search,
                    onValueChange = { search = it },
                    trailingIcon = {
                        TextButton(onClick = {
                            expandedDropdownMenuSearch = true
                        }) {
                            Text(
                                text = searchState.name.lowercase(),
                                color = Color.Red
                            )
                        }
                        DropdownMenu(
                            expanded = expandedDropdownMenuSearch,
                            onDismissRequest = { expandedDropdownMenuSearch = false }
                        ) {
                            SearchState.values().forEach { state ->
                                DropdownMenuItem(onClick = {
                                    searchState = state
                                    expandedDropdownMenuSearch = false
                                }) {
                                    Text(
                                        text = state.name.lowercase(),
                                        color = if (searchState == state) Color.Red else Color.Black
                                    )
                                }
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.Black,
                        disabledTextColor = Color.Black,
                        backgroundColor = Color.White,
                        cursorColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        errorCursorColor = Color.Black
                    )
                )

                when(searchResults){
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
                        if (searchResults.data!!.isEmpty()){
                            LottieAnimation(
                                modifier = Modifier.fillMaxSize(),
                                composition = animationNullable.value,
                                progress = progressAnimationNullable.progress
                            )
                        }else{
                            LazyColumn(content = {
                                items(searchResults.data!!){ item ->

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
                                                mapViewModel.getReverse(
                                                    lat = item.lat,
                                                    lon = item.lon
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