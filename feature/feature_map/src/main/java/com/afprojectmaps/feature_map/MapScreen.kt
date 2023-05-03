package com.afprojectmaps.feature_map

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.afprojectmaps.core_database_domain.model.*
import com.afprojectmaps.core_network_domain.common.Response
import com.afprojectmaps.core_network_domain.entities.infoMap.SearchResult
import com.afprojectmaps.core_network_domain.entities.route.Route
import com.afprojectmaps.core_utils.navigation.MapNavScreen
import com.afprojectmaps.core_utils.navigation.WeatherNavScreen
import com.afprojectmaps.feature_map.common.getGPS
import com.afprojectmaps.feature_map.state.FrontLayerContentState
import com.afprojectmaps.feature_map.state.SearchState
import com.afprojectmaps.feature_map.view.MarkerClickDialogView
import com.afprojectmaps.feature_map.view.TransportRouteDialogView
import com.afprojectmaps.feature_map.viewModel.MapViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@Composable
fun MapScreen(
    navController: NavController,
    mapViewModel: MapViewModel,
    onAdsScreen: () -> Unit
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope

    var search by remember { mutableStateOf("") }
    var route:Route? by remember { mutableStateOf(null) }
    var searchResults:Response<List<SearchResult>> by
        remember { mutableStateOf(Response.Loading()) }
    var history by remember { mutableStateOf(listOf<History>()) }
    var setting by remember { mutableStateOf(Setting()) }

    var homeUser by remember { mutableStateOf(HomeUser()) }
    var workUser by remember { mutableStateOf(WorkUser()) }
    var favoriteMarkerMap by remember { mutableStateOf(listOf<FavoriteMarkerMap>()) }

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
    var frontLayerContentState by remember { mutableStateOf(FrontLayerContentState.FAVORITE) }
    var expandedDropdownMenuFrontLayerContentState by remember { mutableStateOf(false) }

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

    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            mapViewModel.responseHistory.onEach {
                history = it
            }.collect()
        }
    }

    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            mapViewModel.responseSetting.onEach {
                setting = it
            }.collect()
        }
    }

    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            mapViewModel.responseHomeUser.onEach {
                homeUser = it
            }.collect()
        }
    }

    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            mapViewModel.responseWorkUser.onEach {
                workUser = it
            }.collect()
        }
    }

    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            mapViewModel.responseFavoriteMarkerMap.onEach {
                favoriteMarkerMap = it
            }.collect()
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        permission.launchPermissionRequest()
    })

    LaunchedEffect(key1 = search,key2 = searchState, key3 = frontLayerContentState, block = {
        when(frontLayerContentState){
//            FrontLayerContentState.SEARCH_MAP -> {
//                if (search.isNotEmpty()){
//                    mapViewModel.getSearch(
//                        city = if (searchState == SearchState.CITY) search else "",
//                        country = if (searchState == SearchState.COUNTRY) search else "",
//                        county = if (searchState == SearchState.COUNTY) search else "",
//                        postalcode = if (searchState == SearchState.POSTAL_CODE) search else "",
//                        street = if (searchState == SearchState.STREET) search else ""
//                    )
//                }
//            }
            FrontLayerContentState.HISTORY -> {
                mapViewModel.getHistory(search)
            }
            FrontLayerContentState.ROUTE -> Unit
            FrontLayerContentState.FAVORITE -> {
                mapViewModel.getFavoriteMarkerMap(search)
            }
        }
    })

    LaunchedEffect(key1 = searchResult, block = {
        searchResult?.let {
            cameraPosition.position = CameraPosition.fromLatLngZoom(LatLng(it.lat.toDouble(), it.lon.toDouble()), 17f)
        }
    })

    LaunchedEffect(key1 = transport.value,key2 = searchResult, block = {
        if (transport.value.isNotEmpty()){
            searchResult?.let{
                mapViewModel.getRouteUseCase(
                    profile = transport.value,
                    start = "${getGPS(context).longitude},${getGPS(context).latitude}",
                    end = "${searchResult?.lon},${searchResult?.lat}"
                )
                mapViewModel.addHistory(
                    History(
                        id = 0,
                        lat = searchResult!!.lat.toDouble(),
                        lon = searchResult!!.lon.toDouble(),
                        name = searchResult!!.display_name,
                        transport = transport.value
                    )
                )
                frontLayerContentState = FrontLayerContentState.ROUTE
                backdropState.conceal()
            }
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
                        mapStyleOptions = MapStyleOptions(setting.theme.theme)
                    ),
                    onMapClick = {
                        mapViewModel.getReverse(
                            lat = it.latitude.toString(),
                            lon = it.longitude.toString()
                        )
                    },
                    content = {
                        favoriteMarkerMap.forEach { item ->
                            Marker(
                                position = LatLng(item.lat, item.lon),
                                title = item.title,
                                onInfoWindowClick = {
                                    mapViewModel.getReverse(
                                        lat = it.position.latitude.toString(),
                                        lon = it.position.longitude.toString()
                                    )
                                    markerClickDialog.value = true
                                }
                            )
                        }

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

                        if(homeUser.homeName.isNotEmpty()){
                            Marker(
                                title = homeUser.homeName,
                                position = LatLng(
                                    homeUser.homeLat,
                                    homeUser.homeLon
                                ),
                                onInfoWindowClick = {
                                    mapViewModel.getReverse(
                                        lat = it.position.latitude.toString(),
                                        lon = it.position.longitude.toString()
                                    )
                                    markerClickDialog.value = true
                                }
                            )
                        }

                        if(workUser.workName.isNotEmpty()){
                            Marker(
                                title = workUser.workName,
                                position = LatLng(
                                    workUser.workLat,
                                    workUser.workLon
                                ),
                                onInfoWindowClick = {
                                    mapViewModel.getReverse(
                                        lat = it.position.latitude.toString(),
                                        lon = it.position.longitude.toString()
                                    )
                                    markerClickDialog.value = true
                                }
                            )
                        }

                        searchResult?.let {
                            Marker(
                                title = searchResult!!.display_name,
                                draggable = true,
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

                Row {
                    IconButton(
                        onClick = { navController.navigate(MapNavScreen.Setting.route) }
                    ) {
                        Image(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    IconButton(
                        onClick = onAdsScreen
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.money),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    IconButton(
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
            }

        },
        frontLayerContent = {

            MarkerClickDialogView(
                mapViewModel = mapViewModel,
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
//                    if (
////                        frontLayerContentState == FrontLayerContentState.SEARCH_MAP ||
//                        frontLayerContentState == FrontLayerContentState.HISTORY ||
//                        frontLayerContentState == FrontLayerContentState.FAVORITE
//                    ){
//                        OutlinedTextField(
//                            modifier = Modifier.padding(5.dp),
//                            value = search,
//                            onValueChange = { search = it },
//                            trailingIcon = {
////                                if (frontLayerContentState == FrontLayerContentState.SEARCH_MAP){
////                                    TextButton(onClick = {
////                                        expandedDropdownMenuSearch = true
////                                    }) {
////                                        Text(
////                                            text = searchState.name.lowercase(),
////                                            color = Color.Red
////                                        )
////                                    }
////                                    DropdownMenu(
////                                        expanded = expandedDropdownMenuSearch,
////                                        onDismissRequest = { expandedDropdownMenuSearch = false }
////                                    ) {
////                                        SearchState.values().forEach { state ->
////                                            DropdownMenuItem(onClick = {
////                                                searchState = state
////                                                expandedDropdownMenuSearch = false
////                                            }) {
////                                                Text(
////                                                    text = state.name.lowercase(),
////                                                    color = if (searchState == state) Color.Red
////                                                    else Color.Black
////                                                )
////                                            }
////                                        }
////                                    }
////                                }
//                            },
//                            keyboardOptions = KeyboardOptions(
//                                imeAction = ImeAction.Search
//                            ),
//                            colors = TextFieldDefaults.outlinedTextFieldColors(
//                                textColor = Color.Black,
//                                disabledTextColor = Color.Black,
//                                backgroundColor = Color.White,
//                                cursorColor = Color.Black,
//                                focusedBorderColor = Color.Black,
//                                unfocusedBorderColor = Color.Black,
//                                errorCursorColor = Color.Black
//                            )
//                        )
//                    }

                    TextButton(onClick = { expandedDropdownMenuFrontLayerContentState = true }) {
                        Text(
                            text = frontLayerContentState.text.lowercase(),
                            color = Color.Red
                        )
                        DropdownMenu(
                            expanded = expandedDropdownMenuFrontLayerContentState,
                            onDismissRequest = { expandedDropdownMenuFrontLayerContentState = false }
                        ) {
                            FrontLayerContentState.values().forEach { item ->
                                DropdownMenuItem(onClick = {
                                    frontLayerContentState = item
                                    expandedDropdownMenuFrontLayerContentState = false
                                }) {
                                    Text(
                                        text = item.text,
                                        color = if(frontLayerContentState == item) Color.Red else Color.Black
                                    )
                                }
                            }
                        }
                    }
                }

                when(frontLayerContentState){
//                    FrontLayerContentState.SEARCH_MAP -> {
//                        when(searchResults){
//                            is Response.Error -> {
//                                LottieAnimation(
//                                    modifier = Modifier.fillMaxSize(),
//                                    composition = animationError.value,
//                                    progress = progressAnimationError.progress
//                                )
//                            }
//                            is Response.Loading -> {
//                                LottieAnimation(
//                                    modifier = Modifier.fillMaxSize(),
//                                    composition = animationLoading.value,
//                                    progress = progressAnimationLoading.progress
//                                )
//                            }
//                            is Response.Success -> {
//                                if (searchResults.data!!.isEmpty()){
//                                    LottieAnimation(
//                                        modifier = Modifier.fillMaxSize(),
//                                        composition = animationNullable.value,
//                                        progress = progressAnimationNullable.progress
//                                    )
//                                }else{
//                                    LazyColumn(content = {
//                                        items(searchResults.data!!){ item ->
//
//                                            var infoMarker by remember { mutableStateOf(listOf<InfoMarker>()) }
//
//                                            lifecycleScope.launch {
//                                                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
//                                                    mapViewModel.gerInfoMarker(
//                                                        osmIds = item.osm_id
//                                                    ).onEach {
//                                                        infoMarker = it
//                                                    }.collect()
//                                                }
//                                            }
//
//                                            Column(
//                                                modifier = Modifier.pointerInput(Unit){
//                                                    detectTapGestures(onTap = {
//                                                        mapViewModel.getReverse(
//                                                            lat = item.lat,
//                                                            lon = item.lon
//                                                        )
//                                                    })
//                                                }
//                                            ) {
//                                                Text(
//                                                    text = item.display_name,
//                                                    modifier = Modifier
//                                                        .padding(5.dp)
//                                                )
//
//                                                LazyRow(content = {
//                                                    items(infoMarker){ item ->
//                                                        item.extratags.image?.let {
//                                                            Image(
//                                                                painter = rememberImagePainter(
//                                                                    data = item.extratags.image
//                                                                ),
//                                                                contentDescription = null,
//                                                                modifier = Modifier.size(150.dp)
//                                                            )
//                                                        }
//                                                    }
//                                                })
//                                                Divider()
//                                            }
//                                        }
//                                    })
//                                }
//                            }
//                        }
//                    }
                    FrontLayerContentState.HISTORY -> {
                        LazyColumn(content = {
                            items(history){ item ->
                                Column(
                                    modifier = Modifier.combinedClickable(
                                        onClick = {
                                            markerClickDialog.value = true
                                            searchResult = SearchResult(
                                                lat = item.lat.toString(),
                                                lon = item.lon.toString(),
                                                display_name = item.name
                                            )
                                        },
                                        onLongClick = {
                                            mapViewModel.deleteHistoryBayId(item.id)
                                        }
                                    )
                                ) {
                                    Text(
                                        text = item.name,
                                        modifier = Modifier
                                            .padding(5.dp)
                                    )

                                    Text(
                                        text = item.transport,
                                        modifier = Modifier
                                            .padding(5.dp)
                                    )
                                    Divider()
                                }
                            }
                        })
                    }
                    FrontLayerContentState.ROUTE -> {
                        LazyColumn(content = {
                            route?.let{
                                route!!.features.forEach { feature ->
                                    feature.properties.segments.forEach { segment ->
                                        item{
                                            Column {
                                                Text(
                                                    text = "Расстояние ${segment.distance} Метры",
                                                    modifier = Modifier.padding(5.dp),
                                                    color = Color.Red,
                                                    fontWeight = FontWeight.Bold
                                                )

                                                Divider()
                                            }
                                        }

                                        items(segment.steps){ step ->
                                            Column {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Column {
                                                        Text(
                                                            text = step.name,
                                                            modifier = Modifier.padding(5.dp)
                                                        )

                                                        Text(
                                                            text = step.instruction,
                                                            modifier = Modifier.padding(5.dp)
                                                        )
                                                    }

                                                    Column {
                                                        step.way_points.forEach { point ->
                                                            Text(
                                                                text = point.toString(),
                                                                modifier = Modifier.padding(5.dp)
                                                            )
                                                        }
                                                    }
                                                }
                                                Divider()
                                            }
                                        }
                                    }
                                }
                            }
                        })
                    }
                    FrontLayerContentState.FAVORITE -> {
                        LazyColumn(content = {
                            item {
                                Column {
                                    Column(
                                        modifier = Modifier.pointerInput(Unit){
                                            detectTapGestures(
                                                onTap = {
                                                    if (homeUser.homeName.isNotEmpty()){
                                                        markerClickDialog.value = true
                                                        searchResult = SearchResult(
                                                            lat = homeUser.homeLat.toString(),
                                                            lon = homeUser.homeLon.toString(),
                                                            display_name = homeUser.homeName
                                                        )
                                                    }
                                                }
                                            )
                                        }
                                    ) {
                                        Text(
                                            text = "Домой",
                                            modifier = Modifier.padding(5.dp),
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Red
                                        )
                                        Text(
                                            text = homeUser.homeName,
                                            modifier = Modifier.padding(5.dp)
                                        )
                                    }
                                    Column(
                                        modifier = Modifier.pointerInput(Unit){
                                            detectTapGestures(
                                                onTap = {
                                                    if (workUser.workName.isNotEmpty()){
                                                        markerClickDialog.value = true
                                                        searchResult = SearchResult(
                                                            lat = workUser.workLat.toString(),
                                                            lon = workUser.workLon.toString(),
                                                            display_name = workUser.workName
                                                        )
                                                    }
                                                }
                                            )
                                        }
                                    ) {
                                        Text(
                                            text = "Работа",
                                            modifier = Modifier.padding(5.dp),
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Red
                                        )
                                        Text(
                                            text = workUser.workName,
                                            modifier = Modifier.padding(5.dp)
                                        )
                                    }
                                    Divider()
                                }
                            }
                            items(favoriteMarkerMap){ item ->
                                Column(
                                    modifier = Modifier.combinedClickable(
                                        onClick = {
                                            markerClickDialog.value = true
                                            searchResult = SearchResult(
                                                lat = item.lat.toString(),
                                                lon = item.lon.toString(),
                                                display_name = item.title
                                            )
                                        },
                                        onLongClick = {
                                            mapViewModel.deleteFavoriteMarkerMapById(
                                                id = item.id
                                            )
                                        }
                                    )
                                ) {
                                    Text(
                                        text = item.title,
                                        modifier = Modifier.padding(5.dp)
                                    )
                                    Divider()
                                }
                            }
                        })
                    }
                }
            }
        }
    )
}