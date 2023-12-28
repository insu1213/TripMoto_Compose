package com.insu.tripmoto_compose.screen.main.menu.travel_management

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.insu.tripmoto_compose.suitFamily
import com.insu.tripmoto_compose.R.color as AppColor


@Composable
fun TravelExitDialog(
    onDismissRequest: (Int) -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest(0) }) {
        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = 8.dp
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(modifier = Modifier
                    .padding(start = 12.dp, end = 8.dp),
                    text = "여행에서 나가시겠습니까?",
                    fontFamily = suitFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    DialogButton("취소") {
                        onDismissRequest(0)
                    }
                    DialogButton("확인") {
                        onDismissRequest(1)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DialogButton(
    text: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = { onClick() }
    ) {
        Box(
            modifier = Modifier
                .padding(top = 6.dp, start = 12.dp, end = 12.dp, bottom = 6.dp),
        ) {
            Text(
                text = text,
                color = colorResource(id = AppColor.gray_7)
            )
        }
    }
}

@Composable
@Preview
fun DialogButtonPreview() {
    DialogButton("취소") {

    }

}
