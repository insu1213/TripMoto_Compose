package com.insu.tripmoto_compose.screen.main.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.common.composable.BasicButton
import com.insu.tripmoto_compose.common.composable.LimitTextField
import com.insu.tripmoto_compose.common.composable.MainTitleText
import com.insu.tripmoto_compose.R.string as AppText

@Composable
fun EditMarkerDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current
    val viewModel: MapViewModel by hiltViewModel()
    val marker by viewModel.marker

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(8.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
            ) {
                MainTitleText(
                    text = AppText.edit_marker,
                    modifier = Modifier.padding(8.dp),
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
                    text = AppText.title,
                    value = marker.description,
                    onNewValue = viewModel::onDescriptionChange,
                    modifier = Modifier
                        .padding(top = 8.dp)
                )

                Row {
                    BasicButton(
                        text = AppText.cancel,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        onDismiss()
                    }

                    BasicButton(
                        text = AppText.done,
                        modifier = Modifier
                            .fillMaxSize()
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