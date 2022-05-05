package com.example.feature_map.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.core_database_domain.model.FavoriteMarkerMap
import com.example.core_database_domain.model.HomeUser
import com.example.core_database_domain.model.WorkUser
import com.example.core_utils.navigation.WeatherNavScreen
import com.example.feature_map.viewModel.MapViewModel

@Composable
internal fun MarkerClickDialogView(
    mapViewModel:MapViewModel,
    value:MutableState<Boolean>,
    transportDialog:MutableState<Boolean>,
    title:String,
    navController: NavController,
    lat:String,
    lon:String
){
    if (value.value){
        AlertDialog(
            backgroundColor = Color.White,
            shape = AbsoluteRoundedCornerShape(15.dp),
            onDismissRequest = { value.value = false },
            buttons = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        modifier = Modifier.padding(5.dp),
                        color = Color.Black
                    )

                    OutlinedButton(
                        modifier = Modifier.padding(5.dp),
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = "Подробнее")
                    }
                    OutlinedButton(
                        modifier = Modifier.padding(5.dp),
                        onClick = {
                            navController.navigate(WeatherNavScreen.WeatherInfo.data(
                                lat, lon
                            ))
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = "Погода")
                    }
                    OutlinedButton(
                        modifier = Modifier.padding(5.dp),
                        onClick = {
                            value.value = false
                            transportDialog.value = true
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = "Построить маршрут")
                    }

                    Row {
                        OutlinedButton(
                            modifier = Modifier.padding(5.dp),
                            onClick = {
                                mapViewModel.updateHomeUser(
                                    HomeUser(
                                        homeName = title,
                                        homeLon = lon.toDouble(),
                                        homeLat = lat.toDouble()
                                    )
                                )
                                value.value = false
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.Black
                            )
                        ) {
                            Text(text = "Мой дом")
                        }

                        OutlinedButton(
                            modifier = Modifier.padding(5.dp),
                            onClick = {
                                mapViewModel.updateWorkUser(
                                    WorkUser(
                                        workName = title,
                                        workLat = lat.toDouble(),
                                        workLon = lon.toDouble()
                                    )
                                )
                                value.value = false
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.Black
                            )
                        ) {
                            Text(text = "Моя работа")
                        }
                    }
                    OutlinedButton(
                        modifier = Modifier.padding(5.dp),
                        onClick = {
                            mapViewModel.addFavoriteMarkerMap(
                                FavoriteMarkerMap(
                                    id = 0,
                                    title = title,
                                    lat = lat.toDouble(),
                                    lon = lon.toDouble()
                                )
                            )
                            value.value = false
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = "Add favorite")
                    }
                }
            }
        )
    }
}