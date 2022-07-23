package com.sahalnazar.themoviedb_jetpack_compose.ui.screen.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sahalnazar.themoviedb_jetpack_compose.data.model.MovieDetailResponse
import com.sahalnazar.themoviedb_jetpack_compose.data.repository.ComposeTheMovieDbRepository
import com.sahalnazar.themoviedb_jetpack_compose.util.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

data class DetailScreenUiState(
    val movieDetailResponseData: MovieDetailResponse? = null,
    val movieDetailResponseError: String = "",
    val movieDetailResponseLoading: Boolean = true,
    val movieName: String = ""
)

@Serializable
data class DetailScreenArgs(
    val movieId: String,
    val name: String,
)

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val repository: ComposeTheMovieDbRepository
) : ViewModel() {

    var uiState by mutableStateOf(DetailScreenUiState())
        private set

    fun deserializeArgs(args: String) {
        try {
            Json.decodeFromString<DetailScreenArgs>(args).apply {
                fetchMoviesDetails(this.movieId)
                uiState = uiState.copy(movieName = this.name)
            }
        } catch (e: Exception) {
            Log.e("deserializeArgs", "e:: $e")
            uiState = uiState.copy(movieDetailResponseError = "Something went wrong", movieDetailResponseLoading = false)
        }
    }

    private fun fetchMoviesDetails(movieId: String) = viewModelScope.launch {
        uiState = uiState.copy(movieDetailResponseLoading = true)
        uiState = when (val response = repository.fetMovieDetail(movieId = movieId)) {
            is ResultWrapper.Success -> {
                uiState.copy(movieDetailResponseData = response.data, movieDetailResponseLoading = false)
            }
            is ResultWrapper.Failure -> {
                uiState.copy(movieDetailResponseError = response.message, movieDetailResponseLoading = false)
            }
        }
    }

}