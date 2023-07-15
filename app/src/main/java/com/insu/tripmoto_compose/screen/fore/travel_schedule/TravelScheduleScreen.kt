package com.insu.tripmoto_compose.screen.fore.travel_schedule

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.*
import com.insu.tripmoto_compose.common.ext.basicButton
import com.insu.tripmoto_compose.screen.fore.ForeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.insu.tripmoto_compose.R.string as AppText
import com.insu.tripmoto_compose.R.color as AppColor

@SuppressLint("CoroutineCreationDuringComposition", "RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun TravelScheduleScreen(
    openAndPopUp: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ForeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
    var isCalendarOpen by remember { mutableStateOf(false) }
    val state = rememberDateRangePickerState()
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,

        )

    Column(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    isCalendarOpen = false
                })
            }
    ) {
        Column(
            modifier = modifier
                .padding(24.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.8F)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .padding(top = 62.dp)
                    .width(142.dp)
                    .height(142.dp),
                painter = painterResource(R.drawable.calendar),
                contentDescription = null,
            )

            MenuTitleText(modifier = Modifier.padding(top = 56.dp), text = AppText.travel_schedule)

            Row(modifier = Modifier.padding(top = 36.dp)) {
                ReadOnlyBasicField(
                    value = "${uiState.schedule_start} - ${uiState.schedule_end}",
                    onNewValue = {
                    },
                    modifier = Modifier,
                    text = AppText.date,
                    icon = R.drawable.ic_calendar,

                    )

                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    },
                ) {
                    Icon(
                        painter = painterResource(R.drawable.calendar),
                        tint = MaterialTheme.colors.secondary,
                        contentDescription = null,
                        modifier = Modifier
                            .size(26.dp)
                    )
                }
            }

        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicButton(
                AppText.next,
                Modifier
                    .padding(bottom = 24.dp)
                    .basicButton()
            ) {
                //viewModel.placeOnNextClick(openAndPopUp)
            }
        }
    }







    ModalBottomSheetLayout(
        sheetElevation = 3.dp,
        sheetState = bottomSheetState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
                    .background(colorResource(R.color.white))
            ) {
                DateRangePicker(state)
                coroutineScope.launch {
                    bottomSheetState.show()
                }
                Button(
                    onClick = {
                        coroutineScope.launch {
                            bottomSheetState.hide()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(AppColor.primary_800),
                        contentColor = colorResource(AppColor.white)
                    ),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    Text("확인", color = colorResource(R.color.white))
                }
            }
        },
        content = {
            var startDate: String = ""
            var endDate: String = ""
            (if (state.selectedStartDateMillis != null) {
                state.selectedStartDateMillis?.let { getFormattedDate(it) }
            } else {
                "Start Date"
            })?.let { startDate = it }

            (if (state.selectedEndDateMillis != null) {
                state.selectedEndDateMillis?.let { getFormattedDate(it) }
            } else {
                "End Date"
            })?.let { endDate = it }

            viewModel.onDateChange(Pair(startDate, endDate))
        },
        scrimColor = colorResource(R.color.white).copy(alpha = 0.5f),
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    )


}
