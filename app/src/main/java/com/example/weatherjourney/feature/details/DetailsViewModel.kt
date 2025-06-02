package com.example.weatherjourney.feature.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.weatherjourney.core.data.GpsRepository
import com.example.weatherjourney.core.data.LocationRepository
import com.example.weatherjourney.core.data.UserDataRepository
import com.example.weatherjourney.core.data.WeatherRepository
import com.example.weatherjourney.core.domain.ConvertUnitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
// TODO
class DetailsViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val convertUnitUseCase: ConvertUnitUseCase,
    gpsRepository: GpsRepository,
    locationRepository: LocationRepository,
    userDataRepository: UserDataRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

//    private val _detailsRoute = savedStateHandle.toRoute<LocationDetails>()
//    private val _uiState = MutableStateFlow<DetailsUiState>(Loading)
//
//    val uiState = _uiState.asStateFlow()
//    val userData = userDataRepository.userData.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
//        initialValue = null
//    )
//
//    val locationWithWeather = locationRepository.getLocationWithWeather(
//        id = _detailsRoute.locationId
//    ).stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
//        initialValue = null
//    )
//
//    fun refreshWeather() {
//        viewModelScope.launch {
//            weatherRepository.refreshWeatherOfLocation(_detailsRoute.locationId)
//        }
//    }
}

sealed interface DetailsUiState {
    data object Loading : DetailsUiState
    data object Idle : DetailsUiState
}
