package com.ptut.pmovie.core.network

import androidx.paging.PagingData
import com.ptut.pmovie.core.model.Series
import kotlinx.coroutines.flow.Flow

interface TvSeriesNetworkDataSource {
    fun getTrendingThisWeekTvSeries(): Flow<PagingData<Series>>

    fun getOnTheAirTvSeries(): Flow<PagingData<Series>>

    fun getTopRatedTvSeries(): Flow<PagingData<Series>>

    fun getAiringTodayTvSeries(): Flow<PagingData<Series>>

    fun getPopularTvSeries(): Flow<PagingData<Series>>
}
