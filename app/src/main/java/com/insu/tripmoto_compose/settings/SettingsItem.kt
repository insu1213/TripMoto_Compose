package com.insu.tripmoto_compose.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.insu.tripmoto_compose.suitFamily

@Composable
fun SettingsItem(
    item: String,
    callback: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .clickable {
                callback(item)
            }
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
         Text(
             text = item,
             fontSize = 20.sp,
             fontFamily = suitFamily,
             fontWeight = FontWeight.Bold
         )
    }
}