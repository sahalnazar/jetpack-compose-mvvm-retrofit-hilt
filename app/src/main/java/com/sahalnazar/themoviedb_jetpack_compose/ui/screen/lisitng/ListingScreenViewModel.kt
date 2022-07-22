package com.sahalnazar.themoviedb_jetpack_compose.ui.screen.lisitng

import com.sahalnazar.themoviedb_jetpack_compose.data.repository.ComposeTheMovieDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListingScreenViewModel @Inject constructor(
    private val repository: ComposeTheMovieDbRepository
) {

}