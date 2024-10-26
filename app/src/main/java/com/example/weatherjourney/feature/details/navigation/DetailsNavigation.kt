package com.example.weatherjourney.feature.details.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.weatherjourney.feature.details.DetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class LocationDetails(val locationId: Int)

@Serializable
data object CurrentLocationDetails

fun NavController.navigateToCurrentLocationDetails() {
    navigate(route = CurrentLocationDetails)
}

fun NavController.navigateToLocationDetails(locationId: Int) {
    navigate(route = LocationDetails(locationId))
}

fun NavGraphBuilder.detailsScreen(
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    composable<LocationDetails> {
        DetailsScreen(
            onBackClick = onBackClick,
            onSettingsClick = onSettingsClick
        )
    }
}
