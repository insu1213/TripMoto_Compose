package com.insu.tripmoto_compose.screen.main.map.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.BasicColoringButton
import com.insu.tripmoto_compose.common.composable.DropdownContextMenu
import com.insu.tripmoto_compose.common.composable.MainTitleText
import com.insu.tripmoto_compose.common.ext.contextMenu
import com.insu.tripmoto_compose.model.MapMarker
import com.insu.tripmoto_compose.screen.main.map.MapViewModel
import com.insu.tripmoto_compose.suitFamily
import com.insu.tripmoto_compose.R.color as AppColor

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("ResourceType")
@Composable
fun DetailMarkerDialog(
    marker: MapMarker,
    options: List<String>,
    onActionClick: (String) -> Unit,
    onDismiss: () -> Unit,
) {

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(8.dp)
                .width(260.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(12.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = marker.title,
                        color = colorResource(R.color.black),
                        fontSize = 18.sp,
                        fontFamily = suitFamily,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                    )
                    DropdownContextMenu(
                        options,
                        Modifier.contextMenu().padding(start = 20.dp),
                        onActionClick
                    ) {
                        onDismiss()
                    }
                }




                Text(
                    text = marker.description,
                    modifier = Modifier
                        .padding(top = 4.dp, bottom = 4.dp, start = 4.dp)
                        .fillMaxWidth(),
                    color = colorResource(R.color.black),
                    fontSize = 12.sp,
                    fontFamily = suitFamily,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start,
                )

                BasicColoringButton(
                    text = R.string.dismiss,
                    color = AppColor.gray_3,
                    modifier = Modifier
                ) {
                    onDismiss()
                }
            }
        }
    }
}