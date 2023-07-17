package com.insu.tripmoto_compose.screen.fore.travel_expenses

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.BasicButton
import com.insu.tripmoto_compose.common.composable.BasicField
import com.insu.tripmoto_compose.common.composable.MenuTitleText
import com.insu.tripmoto_compose.common.ext.basicButton
import com.insu.tripmoto_compose.screen.fore.ForeViewModel

@Composable
fun TravelExpensesScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ForeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    Column() {
        Column(
            modifier = modifier
                .padding(24.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.8F)
                .verticalScroll(rememberScrollState()),
        ) {
            Image(
                modifier = Modifier
                    .padding(top = 52.dp)
                    .width(142.dp)
                    .height(142.dp),
                painter = painterResource(R.drawable.expenses),
                contentDescription = null,
            )

            MenuTitleText(modifier = Modifier.padding(top = 44.dp), text = R.string.travel_expenses)

            BasicField(
                value = uiState.expenses,
                onNewValue = viewModel::onExpensesChange,
                modifier = Modifier
                    .padding(top = 36.dp, start = 16.dp),
                text = R.string.expenses,
                icon = R.drawable.ic_expenses,
            )
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
                R.string.next,
                Modifier
                    .padding(bottom = 24.dp)
                    .basicButton()
            ) {
                viewModel.expensesOnNextClick(openAndPopUp)
            }
        }
    }
}