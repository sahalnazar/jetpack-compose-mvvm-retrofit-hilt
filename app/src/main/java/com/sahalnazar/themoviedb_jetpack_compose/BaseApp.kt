package com.sahalnazar.themoviedb_jetpack_compose

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sahalnazar.themoviedb_jetpack_compose.ui.theme.ThemoviedbjetpackcomposeTheme
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApp : Application()
