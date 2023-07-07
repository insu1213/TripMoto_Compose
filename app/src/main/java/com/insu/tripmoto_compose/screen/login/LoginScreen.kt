package com.insu.tripmoto_compose.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.*
import com.insu.tripmoto_compose.common.ext.basicButton
import com.insu.tripmoto_compose.common.ext.fieldModifier
import com.insu.tripmoto_compose.R.string as AppText

@Composable
fun LoginScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier
        .padding(24.dp),
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleText()
        SubTitleText()
        
        Image(
            modifier = Modifier
                .width(142.dp)
                .height(142.dp),
            painter = painterResource(R.drawable.hand_bag),
            contentDescription = null,
        )

        MenuTitleText(modifier = Modifier.padding(top = 12.dp), text = AppText.login)
        
        EmailField(uiState.email, viewModel::onEmailChange, Modifier.fieldModifier().padding(top = 36.dp))
        PasswordField(uiState.password, viewModel::onPasswordChange, Modifier.fieldModifier().padding(top = 32.dp))

        BasicButton(AppText.login, Modifier.basicButton().padding(top = 44.dp)) {
            viewModel.onSignInClick(openAndPopUp)
        }


    }
}