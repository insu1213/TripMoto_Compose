package com.insu.tripmoto_compose.screen.member

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MemberList(nickName: String) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
            text = nickName
        )
    }
}