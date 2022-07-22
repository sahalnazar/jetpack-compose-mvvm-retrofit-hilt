package com.sahalnazar.themoviedb_jetpack_compose.ui.screen.lisitng

import androidx.compose.material.SnackbarData
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

    var uiStates by mutableStateOf(ListingScreenUiState())

    init {
        fetchMoviesList()
    }

    private fun fetchMoviesList() = viewModelScope.launch {
        uiStates = uiStates.copy(movieListResponseLoading = true)

        when (val response = repository.fetchMovieList(page = "1")) {
            is ResultWrapper.Success -> {
                uiStates = uiStates.copy(movieListResponseData = response.data)
            }
            is ResultWrapper.Failure -> {
                uiStates = uiStates.copy(movieListResponseError = response.message)
            }
        }
    }
}