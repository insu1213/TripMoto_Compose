package com.insu.tripmoto_compose.common.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.insu.tripmoto_compose.suitFamily
import java.text.SimpleDateFormat
import java.util.*
import com.insu.tripmoto_compose.R.color as AppColor

@SuppressLint("SimpleDateFormat")
fun getFormattedDate(timeInMillis: Long): String{
    val calender = Calendar.getInstance()
    calender.timeInMillis = timeInMillis
    val dateFormat = SimpleDateFormat("yyyy.MM.dd")
    return dateFormat.format(calender.timeInMillis)
}

fun dateValidator(): (Long) -> Boolean {

    return { timeInMillis ->
        val endCalenderDate = Calendar.getInstance()
        endCalenderDate.timeInMillis = timeInMillis
        endCalenderDate.set(Calendar.DATE, Calendar.DATE + 30)
        timeInMillis > Calendar.getInstance().timeInMillis && timeInMillis < endCalenderDate.timeInMillis
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePicker(state: DateRangePickerState) {

    DateRangePicker(state,
        modifier = Modifier,
        dateFormatter = DatePickerFormatter("yy MM dd", "yy MM dd", "yy MM dd"),
        dateValidator = dateValidator(),
        title = {
            Text(
                text = "날짜를 선택해주세요",
                modifier = Modifier
                    .padding(16.dp),
                fontFamily = suitFamily,
                fontWeight = FontWeight.SemiBold
            )
        },
        headline = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Box(Modifier.weight(1f)) {
                    (if(state.selectedStartDateMillis!=null) {
                        state.selectedStartDateMillis?.let { getFormattedDate(it) }
                    }
                    else {
                        "시작일"
                    })?.let { Text(text = it) }
                }
                Box(Modifier.weight(1f)) {
                    (if(state.selectedEndDateMillis!=null) {
                        state.selectedEndDateMillis?.let { getFormattedDate(it) }
                    }
                    else {
                        "종료일"
                    })?.let { Text(text = it) }
                }
            }
        },
        // TODO: 높은 그래픽 리소스 (저사양에서도 실행가능하나 지연이 생길 수 있음)
        showModeToggle = true,
        colors = DatePickerDefaults.colors(
            containerColor = Color.Blue,
            titleContentColor = Color.Black,
            headlineContentColor = Color.Black,
            weekdayContentColor = Color.Black,
            subheadContentColor = Color.Black,
            yearContentColor = Color.Green,
            currentYearContentColor = Color.Red,
            selectedYearContainerColor = Color.Red,
            disabledDayContentColor = Color.Gray,
            todayDateBorderColor = colorResource(AppColor.primary_800),
            dayInSelectionRangeContainerColor = colorResource(AppColor.primary_300),
            dayInSelectionRangeContentColor = Color.White,
            selectedDayContainerColor = colorResource(AppColor.primary_500),
        )
    )
}
