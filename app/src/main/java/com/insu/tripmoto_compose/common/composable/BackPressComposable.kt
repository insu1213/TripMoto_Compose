package com.insu.tripmoto_compose.common.composable

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay

sealed class BackPress {
    object Idle : BackPress()
    object InitialTouch : BackPress()
}

@Composable
fun BackPressCheck() {
    var showToast by remember { mutableStateOf(false) }

    var backPressState by remember { mutableStateOf<BackPress>(BackPress.Idle) }
    val context = LocalContext.current

    if(showToast){
        Toast.makeText(context, "뒤로가기 버튼을 한 번 더 눌러 주세요", Toast.LENGTH_SHORT).show()
        showToast = false
    }


    LaunchedEffect(key1 = backPressState) {
        if (backPressState == BackPress.InitialTouch) {
            delay(2000)
            backPressState = BackPress.Idle
        }
    }

    BackHandler(backPressState == BackPress.Idle) {
        backPressState = BackPress.InitialTouch
        showToast = true
    }
}