package com.insu.tripmoto_compose.screen.first

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.BasicButton
import com.insu.tripmoto_compose.common.composable.SubTitleText
import com.insu.tripmoto_compose.common.composable.TitleText
import com.insu.tripmoto_compose.common.ext.basicButton

@Composable
fun HelloScreen() {
    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleText()
        SubTitleText()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        BasicButton(
            R.string.start,
            Modifier
                .basicButton()
                .align(Alignment.BottomCenter)
                .padding(top = 44.dp)
        ) {
            //viewModel.onSignInClick(openAndPopUp)
        }
    }
}