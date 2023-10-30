package com.insu.tripmoto_compose.screen.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.MainTitleText

@Composable
fun ProfileScreen(
    popUpScreen: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 12.dp, start = 12.dp)
    ) {
        Icon(
            modifier = Modifier
                .padding(end = 4.dp)
                .clickable {
                    popUpScreen()
                },
            painter = painterResource(id = R.drawable.ic_left_arrow),
            contentDescription = ""
        )
        MainTitleText(
            text = R.string.profile_settings
        )
    }
}