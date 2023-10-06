package com.insu.tripmoto_compose.screen.main.map.edit

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.drawable.ShapeDrawable
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.LatLng
import com.insu.tripmoto_compose.common.composable.BasicButton
import com.insu.tripmoto_compose.common.composable.BasicColoringButton
import com.insu.tripmoto_compose.common.composable.DropdownSelector
import com.insu.tripmoto_compose.common.composable.LimitTextField
import com.insu.tripmoto_compose.common.composable.MainTitleText
import com.insu.tripmoto_compose.R.string as AppText
import com.insu.tripmoto_compose.R.color as AppColor

@SuppressLint("ResourceType")
@Composable
fun EditMarkerDialog(
    position: LatLng? = null,
    onDismiss: () -> Unit,
    markerId: String,
    viewModel: EditMarkerViewModel = hiltViewModel()
) {
    val marker by viewModel.marker
    position?.let { viewModel.onPositionChange(it) }

    var colorDialogState by remember { mutableStateOf(false) }
    //var color by remember { mutableStateOf("#000000") }


    LaunchedEffect(Unit) { viewModel.initialize(markerId = markerId) }

    if(colorDialogState) {
        ColorPickerDialog(
            initialColor = "1164CD",
            colors = listOf("FF0000", "00FF00", "0000FF"),
            onChoice = {
                viewModel.onColorChange(it)
                colorDialogState = false
            }
        )
    }

    Dialog(onDismissRequest = {
       // Log.d(TAG, "디버그")
        onDismiss()
    }) {
        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                ) {
                    MainTitleText(
                        modifier = Modifier
                            .align(Alignment.CenterStart),
                        text = AppText.edit_marker,
                    )

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(30.dp)
                            .align(Alignment.CenterEnd)
                            .background(marker.color.toColor(Color.White))
                            .clickable {
                                colorDialogState = true
                            }
                    )
                    //ColorPickerDialog(initialColor = , colors = , onChoice = )
                }

                LimitTextField(
                    maxLength = 15,
                    text = AppText.title,
                    value = marker.title,
                    onNewValue = viewModel::onTitleChange,
                    modifier = Modifier
                        .padding(top = 8.dp)
                )

                LimitTextField(
                    maxLength = 100,
                    text = AppText.description,
                    value = marker.description,
                    onNewValue = viewModel::onDescriptionChange,
                    modifier = Modifier
                        .padding(top = 8.dp)
                )

                Row(
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    BasicColoringButton(
                        text = AppText.cancel,
                        color = AppColor.gray_3,
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        onDismiss()
                    }

                    BasicButton(
                        text = AppText.done,
                        modifier = Modifier

                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        viewModel.onDoneClick() {
                            onDismiss()
                        }
                    }
                }
            }
        }
    }
}