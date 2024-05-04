package com.ptut.pmovie.core.network.ktor

/**
 * Simulates a successful MovieResponse.
 */
const val MOVIE_SUCCESS_JSON = """
    {
        "results": [
            {
                "adult": false,
                "backdrop_path": "/4woSOUD0equAYzvwhWBHIJDCM88.jpg",
                "genre_ids": [28, 27, 53],
                "id": 1096197,
                "original_language": "en",
                "original_title": "No Way Up",
                "overview": "Characters from different backgrounds are thrown together when the plane they're travelling on crashes into the Pacific Ocean. A nightmare fight for survival ensues with the air supply running out and dangers creeping in from all sides.",
                "popularity": 2111.241,
                "poster_path": "/hu40Uxp9WtpL34jv3zyWLb5zEVY.jpg",
                "release_date": "2024-01-18",
                "title": "No Way Up",
                "video": false,
                "vote_average": 6.392,
                "vote_count": 416
            },
            {
                "adult": false,
                "backdrop_path": "/qrGtVFxaD8c7et0jUtaYhyTzzPg.jpg",
                "genre_ids": [28, 878, 12, 14],
                "id": 823464,
                "original_language": "en",
                "original_title": "Godzilla x Kong: The New Empire",
                "overview": "Following their explosive showdown, Godzilla and Kong must reunite against a colossal undiscovered threat hidden within our world, challenging their very existence – and our own.",
                "popularity": 1754.673,
                "poster_path": "/gmGK5Gw5CIGMPhOmTO0bNA9Q66c.jpg",
                "release_date": "2024-03-27",
                "title": "Godzilla x Kong: The New Empire",
                "video": false,
                "vote_average": 6.674,
                "vote_count": 729
            }
        ],
        "page": 1,
        "total_pages": 43755,
        "total_results": 875085,
        "errors": []
    }
"""

const val MOVIE_EMPTY_JSON = """
	{
		"results": [],
		"page": 1,
		"total_pages": 0,
		"total_results": 0,
		"errors": []
	}
"""

/**
 * Simulates an Error MovieApiResponse.
 */
const val MOVIE_ERROR_JSON = """
    {
        "results": [],
        "page": 0,
        "total_pages": 0,
        "total_results": 0,
        "errors": [
            {
                "code": "INVALID_API_KEY",
                "message": "Bad API key. Use x-api-key in the header."
            }
        ]
    }
"""
