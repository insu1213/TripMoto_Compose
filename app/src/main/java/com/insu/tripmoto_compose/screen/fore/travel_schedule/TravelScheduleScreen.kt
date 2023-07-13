package com.insu.tripmoto_compose.screen.fore.travel_schedule

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.ImageButton
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.*
import com.insu.tripmoto_compose.common.ext.basicButton
import com.insu.tripmoto_compose.screen.fore.ForeViewModel
import kotlinx.coroutines.launch
import com.insu.tripmoto_compose.R.string as AppText

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun TravelScheduleScreen(
    openAndPopUp: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ForeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
    var isCalendarOpen by remember { mutableStateOf(false) }



    Column {
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

//            BasicField(
//                value = uiState.schedule_start,
//                onNewValue = {
//
//                },
//                modifier = Modifier
//                    .padding(top = 36.dp)
//                    .clickable {
//                        isCalendarOpen = true
//                    },
//                text = AppText.date,
//                icon = R.drawable.ic_calendar,
//            )

            IconButton(
                onClick = {
                    isCalendarOpen = true
                },
            ) {
                Icon(
                    painter = painterResource(R.drawable.calendar),
                    tint = MaterialTheme.colors.secondary,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                )
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

    if(isCalendarOpen) {
        val state = rememberDateRangePickerState()
        val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        val coroutineScope = rememberCoroutineScope()

        ModalBottomSheetLayout(
            sheetState = bottomSheetState,
            sheetContent = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(600.dp)
                        .background(colorResource(R.color.white))
                ) {
                    DateRangePickerSample(state)
                    coroutineScope.launch {
                        bottomSheetState.show()
                    }
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                isCalendarOpen = false
                                bottomSheetState.hide()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(R.color.black),
                            contentColor = colorResource(R.color.white)
                        ),
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 16.dp)
                    ) {
                        Text("Done", color = colorResource(R.color.white))
                    }
                }
            },
            content = {},
            scrimColor = colorResource(R.color.white).copy(alpha = 0.5f),
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        )

    }
}