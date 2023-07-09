package com.insu.tripmoto_compose.screen.sign_up

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.*
import com.insu.tripmoto_compose.common.ext.basicButton
import com.insu.tripmoto_compose.common.ext.fieldModifier
import com.insu.tripmoto_compose.suitFamily
import com.insu.tripmoto_compose.R.string as AppText

@Composable
fun SignUpScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
    val fieldModifier = Modifier.fieldModifier()

    Column(
        modifier = modifier
            .padding(24.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleText()
        SubTitleText()
        Image(
            modifier = Modifier
                .width(142.dp)
                .height(142.dp)
                .padding(top = 12.dp),
            painter = painterResource(R.drawable.plane),
            contentDescription = null,
        )

        MenuTitleText(modifier = Modifier.padding(top = 12.dp), text = AppText.register)

        EmailField(
            uiState.email,
            viewModel::onEmailChange,
            Modifier
                .fieldModifier()
                .padding(top = 36.dp)
        )
        PasswordField(
            uiState.password, viewModel::onPasswordChange, Modifier
                .fieldModifier()
                .padding(top = 32.dp)
        )
        RepeatPasswordField(
            uiState.repeatPassword, viewModel::onRepeatPasswordChange, Modifier
                .fieldModifier()
                .padding(top = 32.dp)
        )

        BasicButton(
            AppText.create_account,
            Modifier
                .basicButton()
                .padding(top = 44.dp)
        ) {
            viewModel.onSignUpClick(openAndPopUp)
        }

        Row(
            modifier = Modifier
                .padding(top = 8.dp)
        ) {
            Text(
                "Did you sign up before?",
                fontFamily = suitFamily,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.gray_6),
                fontSize = 12.sp
            )

            Text(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable(enabled = true) {
                        viewModel.goSignIn(openAndPopUp)
                    },
                text = "Sign in",
                fontFamily = suitFamily,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.primary_800),
                fontSize = 12.sp,
            )
        }
    }
}