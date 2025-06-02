package com.example.weatherjourney.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherjourney.core.data.UserDataRepository
import com.example.weatherjourney.core.datastore.model.UserData
import com.example.weatherjourney.core.domain.GetLiveLocalWeatherUseCase
import com.example.weatherjourney.core.model.LocationWithWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userDataRepository: UserDataRepository,
    getLiveLocalWeatherUseCase: GetLiveLocalWeatherUseCase,
) : ViewModel() {

    data class UiState(
        val liveLocalWeather: LocationWithWeather,
        val userData: UserData,
        val isLoading: Boolean,
    )

    private val _isLoading = MutableStateFlow(false)

    private val _liveLocalWeather = getLiveLocalWeatherUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    private val _userData = userDataRepository.userData
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    val uiState = combine(
        _liveLocalWeather.filterNotNull(),
        _userData.filterNotNull(),
        _isLoading,
    ) { liveLocalWeather, userData, isLoading ->

        UiState(
            liveLocalWeather = liveLocalWeather,
            userData = userData,
            isLoading = isLoading,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )
}
