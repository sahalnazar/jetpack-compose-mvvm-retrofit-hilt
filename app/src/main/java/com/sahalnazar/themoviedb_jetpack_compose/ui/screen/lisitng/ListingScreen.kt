package com.sahalnazar.themoviedb_jetpack_compose.ui.screen.lisitng

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sahalnazar.themoviedb_jetpack_compose.data.model.MovieListResponse
import com.sahalnazar.themoviedb_jetpack_compose.navigation.ComposeTheMovieDbScreens
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun ListingScreen(
    navController: NavHostController,
    viewModel: ListingScreenViewModel
) {

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Browse Movies", style = MaterialTheme.typography.subtitle1) }
            )
        }, bottomBar = {
            Spacer(modifier = Modifier.windowInsetsBottomHeight(insets = WindowInsets.navigationBars))
        },
        backgroundColor = Color.White
    ) {


        Box(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = it.calculateBottomPadding(), top = 16.dp),
            ) {
                viewModel.uiStates.movieListResponseData?.results?.forEach {
                    item {
                        MoviesListingItem(
                            data = it,
                            onClick = {
                                navController.navigate(ComposeTheMovieDbScreens.DetailScreen.route + "/" + "sdf")
                            }
                        )
                    }
                }

                if (viewModel.uiStates.movieListResponseData?.page == viewModel.uiStates.movieListResponseData?.totalPages) {
                    item { Text(text = "List end reached") }
                }
            }

            if (viewModel.uiStates.movieListResponseLoading
                && (viewModel.uiStates.movieListResponseData == null)
            ) {
                CircularProgressIndicator()
            }
        }
    }

}

@Composable
fun MoviesListingItem(data: MovieListResponse.Result?, onClick: () -> Unit) {
    Text(text = data?.title ?: "no title")
}
