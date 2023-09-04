package com.insu.tripmoto_compose.screen.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.R
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(
    openAndPopUp: (String) -> Unit,
    foreScreen: String? = null,
    mainThreadDelayTime: Long = 1000,
    viewModel: LoadingViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = colorResource(R.color.primary_800),
        )
    }

    LaunchedEffect(true) {
        delay(mainThreadDelayTime)
        viewModel.onNextScreen(openAndPopUp)
    }
}