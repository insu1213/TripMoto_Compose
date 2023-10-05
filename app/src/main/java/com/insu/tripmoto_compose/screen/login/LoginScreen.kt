package com.insu.tripmoto_compose.screen.login

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.*
import com.insu.tripmoto_compose.common.ext.basicButton
import com.insu.tripmoto_compose.common.ext.fieldModifier
import com.insu.tripmoto_compose.resources
import com.insu.tripmoto_compose.suitFamily
import com.insu.tripmoto_compose.R.string as AppText

@SuppressLint("ResourceType")
@Composable
fun LoginScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    BackOnPressed()

    Column(
        modifier = modifier
            .padding(24.dp)
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
        
        EmailField(uiState.email, viewModel::onEmailChange,
            Modifier
                .fieldModifier()
                .padding(top = 36.dp))
        PasswordField(uiState.password, viewModel::onPasswordChange,
            Modifier
                .fieldModifier()
                .padding(top = 32.dp))

        BasicButton(AppText.login,
            Modifier
                .basicButton()
                .padding(top = 44.dp)) {
            viewModel.onSignInClick(openAndPopUp)
        }

        Row(
            modifier = Modifier
                .padding(top = 8.dp)
        ) {
            Text(
                stringResource(AppText.goto_sign_up),
                fontFamily = suitFamily,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.gray_6),
                fontSize = 12.sp
            )

            Text(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable(enabled = true) {
                        viewModel.goSignUp(openAndPopUp)
                    },
                text = stringResource(AppText.sign_up),
                fontFamily = suitFamily,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.primary_800),
                fontSize = 12.sp,
            )
        }

//        Row(
//            modifier = Modifier
//                .padding(top = 18.dp),
//        ) {
//            Box(
//                modifier = Modifier
//                    .background(colorResource(R.color.gray_2))
//                    .fillMaxWidth(0.9F)
//                    .height(1.dp)
//            )
//        }
    }
}