package com.insu.tripmoto_compose.screen.main.menu.travel_management

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.insu.tripmoto_compose.suitFamily


@Composable
fun TravelExitDialog(
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = 8.dp
        ) {
            Column() {
                Text(modifier = Modifier
                    .padding(start = 12.dp, end = 8.dp),
                    text = "여행에서 나가시겠습니까?",
                    fontFamily = suitFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }
        }
    }
}