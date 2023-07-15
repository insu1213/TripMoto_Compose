package com.insu.tripmoto_compose.screen.fore.travel_place

import androidx.compose.material.icons.Icons
import androidx.compose.material.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.api.ResourceProto.resource
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.BasicButton
import com.insu.tripmoto_compose.common.composable.BasicField
import com.insu.tripmoto_compose.common.composable.MenuTitleText
import com.insu.tripmoto_compose.common.ext.basicButton
import com.insu.tripmoto_compose.screen.fore.ForeViewModel
import com.insu.tripmoto_compose.R.string as AppText
import com.insu.tripmoto_compose.R.drawable as AppIcon


@Composable
fun TravelPlaceScreen(
    openAndPopUp: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ForeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

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
                painter = painterResource(R.drawable.place_marker),
                contentDescription = null,
            )

            MenuTitleText(modifier = Modifier.padding(top = 56.dp), text = AppText.travel_place)

            BasicField(
                value = uiState.nation,
                onNewValue = viewModel::onNationChange,
                modifier = Modifier
                    .padding(top = 36.dp),
                text = AppText.nation,
                icon = R.drawable.ic_nation,
            )

            BasicField(
                value = uiState.city,
                onNewValue = viewModel::onCityChange,
                modifier = Modifier
                    .padding(top = 32.dp),
                text = AppText.city,
                icon = R.drawable.ic_city,
            )

            MenuTitleText(modifier = Modifier.padding(top = 56.dp), text = AppText.number_of_rooms)
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
                viewModel.placeOnNextClick(openAndPopUp)
            }
        }
    }
}