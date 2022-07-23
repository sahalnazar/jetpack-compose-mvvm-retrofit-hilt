package com.sahalnazar.themoviedb_jetpack_compose.ui.screen.lisitng

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sahalnazar.themoviedb_jetpack_compose.data.model.MovieListResponse
import com.sahalnazar.themoviedb_jetpack_compose.data.repository.ComposeTheMovieDbRepository
import com.sahalnazar.themoviedb_jetpack_compose.util.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ListingScreenUiState(
    val movieListResponseData: MovieListResponse? = null,
    val movieListResponseError: String = "",
    val movieListResponseLoading: Boolean = true,

    val snackbarMessage: String = ""
)

@HiltViewModel
class ListingScreenViewModel @Inject constructor(
    private val repository: ComposeTheMovieDbRepository
) : ViewModel() {

    var uiState by mutableStateOf(ListingScreenUiState())
        private set

    fun fetchMoviesList() = viewModelScope.launch {
        uiState = uiState.copy(movieListResponseLoading = true)
        val page = uiState.movieListResponseData?.page?.let { (it + 1).toString() } ?: "1"
        when (val response = repository.fetchMovieList(page = page)) {
            is ResultWrapper.Success -> {
                if (uiState.movieListResponseData == null) {
                    uiState = uiState.copy(movieListResponseData = response.data, movieListResponseLoading = false)
                } else {
                    val existingList = uiState.movieListResponseData?.results.orEmpty()
                    val responseList = response.data?.results.orEmpty()
                    val appendedList = existingList.plus(responseList)
                    uiState = uiState.copy(
                        movieListResponseData = MovieListResponse(
                            page = response.data?.page,
                            results = appendedList,
                            totalPages = response.data?.totalPages,
                            totalResults = response.data?.totalResults
                        ), movieListResponseLoading = false
                    )
                }
            }
            is ResultWrapper.Failure -> {
                uiState = uiState.copy(movieListResponseError = response.message, movieListResponseLoading = false)
            }
        }
    }

    fun hasNextPage(): Boolean {
        return (uiState.movieListResponseData?.page ?: 0) < (uiState.movieListResponseData?.totalPages ?: 0)
    }
}