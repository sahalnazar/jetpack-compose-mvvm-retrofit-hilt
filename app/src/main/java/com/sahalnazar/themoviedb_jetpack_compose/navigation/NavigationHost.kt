package com.sahalnazar.themoviedb_jetpack_compose.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.sahalnazar.themoviedb_jetpack_compose.ui.screen.detail.DetailScreen
import com.sahalnazar.themoviedb_jetpack_compose.ui.screen.lisitng.ListingScreen


sealed class ComposeTheMovieDbScreens(
    val route: String
) {
    object ListingScreen : ComposeTheMovieDbScreens("listing_screen")
    object DetailScreen : ComposeTheMovieDbScreens("detail_screen")
}

const val DETAIL_SCREEN_ARGS = "DETAIL_SCREEN_ARGS"
const val DEF_DETAIL_SCREEN_ARGS = "DEF_DETAIL_SCREEN_ARGS"


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationHost(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = ComposeTheMovieDbScreens.ListingScreen.route,
        enterTransition = {
            fadeIn()
        },
        exitTransition = {
            fadeOut()
        },
        popExitTransition = {
            fadeOut()
        },
        popEnterTransition = {
            fadeIn()
        }
    ) {


        composable(
            route = ComposeTheMovieDbScreens.ListingScreen.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(700))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(700))
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(700))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(700))
            }

            ) { backStackEntry ->
            ListingScreen(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }

        composable(
            route = ComposeTheMovieDbScreens.DetailScreen.route
                    + "/{$DETAIL_SCREEN_ARGS}",
            arguments = listOf(
                navArgument(DETAIL_SCREEN_ARGS) {
                    type = NavType.StringType
                }
            ),

        ) { backStackEntry ->

            DetailScreen(
                navController = navController,
                args = backStackEntry.arguments?.getString(DETAIL_SCREEN_ARGS) ?: DEF_DETAIL_SCREEN_ARGS,
                viewModel = hiltViewModel()
            )
        }

    }
}