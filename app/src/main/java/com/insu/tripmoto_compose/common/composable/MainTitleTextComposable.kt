package com.insu.tripmoto_compose.common.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.suitFamily
import com.insu.tripmoto_compose.R.string as AppText

@Composable
fun TitleText(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = stringResource(AppText.trip_moto),
        color = colorResource(R.color.primary_800),
        fontSize = 46.sp,
        fontFamily = suitFamily,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun SubTitleText() {
    Text(
        modifier = Modifier
            .padding(top = 8.dp),
        text = stringResource(AppText.new_guide_of_trip),
        color = colorResource(R.color.primary_800),
        fontSize = 16.sp,
        fontFamily = suitFamily,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun MenuTitleText(
    modifier: Modifier = Modifier,
    @StringRes text: Int
) {
    Text(
        modifier = modifier
            .fillMaxWidth(),
        text = stringResource(text),
        color = colorResource(R.color.black),
        fontSize = 30.sp,
        fontFamily = suitFamily,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start,
    )
}

@Composable
fun MainTitleText(
    modifier: Modifier = Modifier,
    @StringRes text: Int
) {
    Text(
        text = stringResource(text),
        color = colorResource(R.color.black),
        fontSize = 18.sp,
        fontFamily = suitFamily,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Start,
    )
}