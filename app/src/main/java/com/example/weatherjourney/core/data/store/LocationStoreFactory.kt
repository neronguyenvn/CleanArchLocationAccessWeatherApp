package com.example.weatherjourney.core.data.store

import com.example.weatherjourney.core.data.mapper.asEntity
import com.example.weatherjourney.core.database.LocationDao
import com.example.weatherjourney.core.database.model.LocationEntity
import com.example.weatherjourney.core.database.model.asLocation
import com.example.weatherjourney.core.model.Coordinate
import com.example.weatherjourney.core.model.Location
import com.example.weatherjourney.core.network.NetworkDataSource
import com.example.weatherjourney.core.network.model.LocationNetworkModel
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.store5.Converter
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder

typealias LocationStore = Store<Coordinate, Location>

class LocationStoreFactory(
    private val networkDataSource: NetworkDataSource,
    private val locationDao: LocationDao,
) {
    fun create(): LocationStore {
        return StoreBuilder.from(
            fetcher = createFetcher(),
            sourceOfTruth = createSourceOfTruth(),
            converter = createConverter()
        ).build()
    }

    private fun createFetcher(): Fetcher<Coordinate, LocationNetworkModel> =
        Fetcher.of { coordinate ->
            networkDataSource.getLocation(coordinate)
        }

    private fun createSourceOfTruth(): SourceOfTruth<Coordinate, LocationEntity, Location> =
        SourceOfTruth.of(
            reader = { coordinate ->
                locationDao
                    .observe(coordinate.latitude, coordinate.longitude)
                    .map { it.asLocation() }
            },
            writer = { _, locationEntity ->
                locationDao.insert(locationEntity)
            }
        )

    private fun createConverter(): Converter<LocationNetworkModel, LocationEntity, Location> =
        Converter.Builder<LocationNetworkModel, LocationEntity, Location>()
            .fromOutputToLocal { location -> location.asEntity() }
            .fromNetworkToLocal { location -> location.asEntity() }
            .build()
}
