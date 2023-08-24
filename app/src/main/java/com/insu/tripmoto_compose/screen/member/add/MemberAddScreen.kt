package com.insu.tripmoto_compose.screen.member.add

import android.telecom.CallScreeningService
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.common.composable.ReadOnlyBasicField
import com.insu.tripmoto_compose.R.string as AppText

@Composable
fun MemberAddScreen(
    viewModel: MemberAddViewModel = hiltViewModel(),
    popUpScreen: () -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
    ) {
        Column(modifier = Modifier
            .align(Alignment.Center)
        ) {
            Text(text = "초대코드가 생성되었습니다!")
            Text(text = viewModel.code)
        }
    }
}