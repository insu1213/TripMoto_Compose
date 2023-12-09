package com.insu.tripmoto_compose.screen.member.add

import android.telecom.CallScreeningService
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.ReadOnlyBasicField
import com.insu.tripmoto_compose.common.snackbar.SnackbarManager
import com.insu.tripmoto_compose.suitFamily
import com.insu.tripmoto_compose.R.string as AppText
import com.insu.tripmoto_compose.R.drawable as AppIcon

@Composable
fun MemberAddScreen(
    viewModel: MemberAddViewModel = hiltViewModel(),
    popUpScreen: () -> Unit,
) {
    val activity = LocalContext.current as ComponentActivity
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 12.dp, top = 12.dp, end = 4.dp)
                .align(Alignment.TopStart)
                .clickable {
                    viewModel.popUpBackStack(popUpScreen)
                },
            painter = painterResource(id = R.drawable.ic_left_arrow),
            contentDescription = ""
        )
        Column(modifier = Modifier
            .align(Alignment.Center)
        ) {
            Text(
                text = stringResource(AppText.invite_code_generation),
                color = colorResource(R.color.black),
                fontSize = 20.sp,
                fontFamily = suitFamily,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )

            Row() {
                Text(
                    text = stringResource(AppText.invite_code) + ": " + viewModel.code.value,
                    color = colorResource(R.color.black),
                    fontSize = 16.sp,
                    fontFamily = suitFamily,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                )
                Icon(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable {
                            viewModel.copyText(clipboardManager)
                            SnackbarManager.showMessage(AppText.copied)
                        },
                    painter = painterResource(id = AppIcon.ic_copy),
                    contentDescription = null
                )
            }


        }
    }
}