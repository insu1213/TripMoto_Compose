package com.insu.tripmoto_compose.screen.main.map.edit

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.LatLng
import com.insu.tripmoto_compose.common.composable.BasicButton
import com.insu.tripmoto_compose.common.composable.BasicColoringButton
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

    LaunchedEffect(Unit) { viewModel.initialize(markerId = markerId) }

    Dialog(onDismissRequest = {
        onDismiss()

    }) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(8.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MainTitleText(
                    text = AppText.edit_marker,
                    modifier = Modifier.padding(24.dp),
                )

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