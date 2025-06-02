package com.example.weatherjourney.feature.details.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable


@Serializable
data object CurrentLocationDetails

// TODO
fun NavController.navigateToCurrentLocationDetails() {
    navigate(route = CurrentLocationDetails)
}
/*

fun NavController.navigateToLocationDetails(locationId: Int) {
    navigate(route = LocationDetails(locationId))
}

fun NavGraphBuilder.detailsScreen(
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    composable<LocationDetails> {
//        DetailsScreen(
//            onBackClick = onBackClick,
//            onSettingsClick = onSettingsClick
//        )
    }
}
*/
