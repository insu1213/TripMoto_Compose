package com.insu.tripmoto_compose.screen.member

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.MainTitleText

@Composable
fun MemberScreen(
    viewModel: MemberViewModel = hiltViewModel(),
    openScreen: (String) -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 12.dp, start = 12.dp, end = 12.dp)
    ) {
        MainTitleText(
            modifier = Modifier.align(Alignment.CenterStart),
            text = R.string.travel_members
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable {
                    viewModel.onAddClick(openScreen)
                },
            text = "초대하기",
            color = colorResource(R.color.primary_800)
        )
    }
}