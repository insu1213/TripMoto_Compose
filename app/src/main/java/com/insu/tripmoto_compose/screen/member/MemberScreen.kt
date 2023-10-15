package com.insu.tripmoto_compose.screen.member

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.MainTitleText
import com.insu.tripmoto_compose.screen.trip_selection.TripItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MemberScreen(
    viewModel: MemberViewModel = hiltViewModel(),
    openScreen: (String) -> Unit
) {
    var members by remember { mutableStateOf<List<String>>(mutableListOf("")) }

    viewModel.getMember() {
        members = it
    }


    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 12.dp, end = 12.dp)
        ) {
            MainTitleText(
                modifier = Modifier.align(Alignment.CenterStart),
                text = R.string.travel_members
            )

            Text(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable {
                        viewModel.onAddClick(openScreen)
                    },
                text = "초대하기",
                color = colorResource(R.color.primary_800)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 12.dp)
        ) {
            Log.d(TAG, "member3: d")
            itemsIndexed(members) { _, item ->
                MemberList(item)
            }
        }
    }
}