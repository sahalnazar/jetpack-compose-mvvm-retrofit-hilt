package com.sahalnazar.themoviedb_jetpack_compose.ui.screen.lisitng

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.sahalnazar.themoviedb_jetpack_compose.BuildConfig
import com.sahalnazar.themoviedb_jetpack_compose.data.model.MovieListResponse
import com.sahalnazar.themoviedb_jetpack_compose.navigation.ComposeTheMovieDbScreens
import com.sahalnazar.themoviedb_jetpack_compose.ui.screen.detail.DetailScreenArgs
import com.sahalnazar.themoviedb_jetpack_compose.util.ExtensionFunctions.isLastItemVisible
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun ListingScreen(
    navController: NavHostController,
    viewModel: ListingScreenViewModel
) {

    val uiState = viewModel.uiState
    val lazyState = rememberLazyListState()

    LaunchedEffect(key1 = lazyState.isLastItemVisible, block = {
        if (lazyState.isLastItemVisible && !uiState.movieListResponseLoading && viewModel.hasNextPage()) {
            viewModel.fetchMoviesList()
        }
    })

    LaunchedEffect(key1 = true, block = {
        if (uiState.movieListResponseData == null) {
            viewModel.fetchMoviesList()
        }
    })

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Browse Movies", style = MaterialTheme.typography.h6) }
            )
        }, bottomBar = {
            Spacer(modifier = Modifier.windowInsetsBottomHeight(insets = WindowInsets.navigationBars))
        },
        backgroundColor = Color.White
    ) {


        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                state = lazyState,
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp, top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                uiState.movieListResponseData?.results?.forEach {
                    item {
                        MoviesListingItem(
                            modifier = Modifier.padding(horizontal = 14.dp),
                            data = it,
                            onClick = {
                                navController.navigate(
                                    ComposeTheMovieDbScreens.DetailScreen.route + "/"
                                            + Json.encodeToString(
                                        DetailScreenArgs(
                                            movieId = it?.id?.toString() ?: "",
                                            name = it?.title ?: ""
                                        )
                                    )
                                )
                            }
                        )
                    }
                }

                if (viewModel.uiState.movieListResponseLoading
                    && (viewModel.uiState.movieListResponseData?.results?.isNotEmpty() == true)
                ) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxWidth()
                        )
                    }
                }

                if (viewModel.uiState.movieListResponseData?.page != null && viewModel.uiState.movieListResponseData?.page == viewModel.uiState.movieListResponseData?.totalPages) {
                    item { Text(text = "List end reached", Modifier.fillMaxWidth()) }
                }

                if (viewModel.uiState.movieListResponseError.isNotEmpty()) {
                    item { Text(text = viewModel.uiState.movieListResponseError, modifier = Modifier.align(Alignment.Center)) }
                }
            }

            if (viewModel.uiState.movieListResponseLoading
                && (viewModel.uiState.movieListResponseData == null)
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoviesListingItem(modifier: Modifier, data: MovieListResponse.Result?, onClick: () -> Unit) {
    Surface(
        modifier = modifier
            .height(126.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colors.surface,
        onClick = onClick,
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            AsyncImage(
                model = BuildConfig.BASE_IMG_URL + data?.posterPath,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight(),
                contentScale = ContentScale.FillHeight
            )


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 14.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.SpaceBetween

            ) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = data?.title ?: "",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(text = data?.voteAverage?.let { "Rating: $it" } ?: "", style = MaterialTheme.typography.body1)
                }
                Text(text = data?.releaseDate?.let { "Release date: $it" } ?: "", style = MaterialTheme.typography.caption)
            }
        }
    }
}
