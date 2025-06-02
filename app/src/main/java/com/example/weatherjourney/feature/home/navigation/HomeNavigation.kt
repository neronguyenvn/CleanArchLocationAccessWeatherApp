package com.example.weatherjourney.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.weatherjourney.feature.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

// TODO
fun NavGraphBuilder.homeScreen(
    onSearchClick: () -> Unit,
    onLocationClick: (Int) -> Unit,
) {
    composable<HomeRoute> {
        HomeRoute(
            onBackClick = {},
            onSettingsClick = {}
        )
    }
}
