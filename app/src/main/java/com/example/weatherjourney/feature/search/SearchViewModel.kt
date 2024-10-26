package com.example.weatherjourney.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherjourney.core.data.LocationRepository
import com.example.weatherjourney.core.model.Location
import com.example.weatherjourney.feature.search.SearchUiEvent.NavigateToDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
) : ViewModel() {

    private val _uiEvent = Channel<SearchUiEvent>()
    private val _query = MutableStateFlow("")

    val uiEvent = _uiEvent.receiveAsFlow()
    val query = _query.asStateFlow()

    val searchResults = query.flatMapLatest {
        if (it.length < SEARCH_QUERY_MIN_LENGTH) {
            return@flatMapLatest flowOf(emptyList())
        }
        locationRepository.getLocationsByAddress(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
        initialValue = emptyList()
    )

    fun onQueryChanged(query: String) {
        _query.update { query }
    }

    fun saveLocation(location: Location) {
        viewModelScope.launch {
            val locationId = locationRepository.saveAndRefreshLocation(location)
            _uiEvent.send(NavigateToDetails(locationId))
        }
    }
}

private const val SEARCH_QUERY_MIN_LENGTH = 3

sealed interface SearchUiEvent {
    data class NavigateToDetails(val locationId: Int) : SearchUiEvent
}
