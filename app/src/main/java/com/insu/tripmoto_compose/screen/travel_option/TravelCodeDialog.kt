package com.insu.tripmoto_compose.screen.travel_option

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.LatLng
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.BasicButton
import com.insu.tripmoto_compose.common.composable.BasicColoringButton
import com.insu.tripmoto_compose.common.composable.LimitTextField
import com.insu.tripmoto_compose.common.composable.MainTitleText
import com.insu.tripmoto_compose.screen.main.map.edit.EditMarkerViewModel
import com.insu.tripmoto_compose.R.color as AppColor

@SuppressLint("ResourceType")
@Composable
fun TravelCodeDialog(
    onDismiss: (Boolean) -> Unit,
    viewModel: TravelOptionViewModel = hiltViewModel()
) {
    Dialog(onDismissRequest = {
        onDismiss(false)
    }) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(12.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MainTitleText(
                    text = R.string.enter_invite_code,
                    modifier = Modifier.padding(8.dp),
                )

//                LimitTextField(
//                    maxLength = 10,
//                    text = R.string.code,
//                    value = viewModel.code.value,
//                    onNewValue = viewModel::onChangeCode,
//                    modifier = Modifier
//                        .padding(top = 8.dp)
//                )
                CodeTextField(
                    modifier = Modifier.padding(top = 8.dp),
                    text = viewModel.code.value,
                    onTextChanged = viewModel::onChangeCode
                )

                Row(
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    BasicColoringButton(
                        text = R.string.cancel,
                        color = AppColor.gray_3,
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        onDismiss(false)
                    }

                    BasicButton(
                        text = R.string.done,
                        modifier = Modifier

                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        viewModel.onDoneClick() { isValid ->
                            if(isValid) {
                                onDismiss(true)
                            }
                        }
                    }
                }
            }
        }
    }
}