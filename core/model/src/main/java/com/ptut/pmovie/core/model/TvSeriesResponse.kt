package com.ptut.pmovie.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvSeriesResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<Series>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int,
)
