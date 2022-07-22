package com.sahalnazar.themoviedb_jetpack_compose.ui.screen.detail

import androidx.lifecycle.ViewModel
import com.sahalnazar.themoviedb_jetpack_compose.data.repository.ComposeTheMovieDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val repository: ComposeTheMovieDbRepository
) : ViewModel() {
}