package com.insu.tripmoto_compose.screen.fore.travel_schedule

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.BasicButton
import com.insu.tripmoto_compose.common.composable.BasicField
import com.insu.tripmoto_compose.common.composable.MenuTitleText
import com.insu.tripmoto_compose.common.composable.SampleDatePickerView
import com.insu.tripmoto_compose.common.ext.basicButton
import com.insu.tripmoto_compose.screen.fore.ForeViewModel
import com.insu.tripmoto_compose.R.string as AppText

@Composable
fun TravelScheduleScreen(
    openAndPopUp: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ForeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
    var isCalendarOpen by remember { mutableStateOf(false) }

    if(isCalendarOpen) {
        Log.d(TAG, "실행됨")
        SampleDatePickerView()
    }

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
                isCalendarOpen = true
            }
        }
    }
}
