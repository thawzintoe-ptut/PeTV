package com.ptut.pmovie.core.data.di

import com.ptut.pmovie.core.data.utils.ConnectivityManagerNetworkMonitor
import com.ptut.pmovie.core.data.utils.NetworkMonitor
import com.ptut.pmovie.core.data.utils.TimeZoneBroadcastMonitor
import com.ptut.pmovie.core.data.utils.TimeZoneMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindsNetworkMonitor(networkMonitor: ConnectivityManagerNetworkMonitor): NetworkMonitor

    @Binds
    internal abstract fun binds(impl: TimeZoneBroadcastMonitor): TimeZoneMonitor
}
