package com.insu.tripmoto_compose.common.composable

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.suitFamily

@Composable
fun BasicButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit) {
    Button(
        onClick = action,
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        shape = RoundedCornerShape(24.dp),
        colors =
        ButtonDefaults.buttonColors(
            backgroundColor = colorResource(R.color.primary_800),
            contentColor = MaterialTheme.colors.onPrimary
        )
    ) {
        Text(
            text = stringResource(text),
            fontSize = 16.sp,
            fontFamily = suitFamily,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(R.color.white)
        )
    }
}

@SuppressLint("ResourceType")
@Composable
fun BasicColoringButton(@StringRes text: Int, modifier: Modifier, @StringRes color: Int, action: () -> Unit) {
    Button(
        onClick = action,
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        shape = RoundedCornerShape(24.dp),
        colors =
        ButtonDefaults.buttonColors(
            backgroundColor = colorResource(color),
            contentColor = MaterialTheme.colors.onPrimary
        )
    ) {
        Text(
            text = stringResource(text),
            fontSize = 16.sp,
            fontFamily = suitFamily,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(R.color.gray_8)
        )
    }
}


@Composable
fun BasicTextButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit) {
    TextButton(onClick = action, modifier = modifier) { Text(text = stringResource(text)) }
}