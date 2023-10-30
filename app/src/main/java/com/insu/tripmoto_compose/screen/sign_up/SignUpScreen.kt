package com.insu.tripmoto_compose.screen.sign_up

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.Circle
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.*
import com.insu.tripmoto_compose.common.ext.basicButton
import com.insu.tripmoto_compose.common.ext.fieldModifier
import com.insu.tripmoto_compose.screen.main.wishlist.edit.WishListEditViewModel
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

    BackOnPressed()

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

//        Surface(
//            modifier = Modifier.size(80.dp),
//            shape = CircleShape,
//        ) {
//
//        }

        RequestContentPermission(viewModel) { uri ->
            viewModel.onImageResourceChange(uri)
        }

        LimitTextField(
            modifier = Modifier.padding(top = 20.dp),
            maxLength = 10,
            text = AppText.nick_name,
            value = uiState.nickName,
            onNewValue = viewModel::onNickNameChange
        )

        EmailField(
            uiState.email,
            viewModel::onEmailChange,
            Modifier
                .fieldModifier()
                .padding(top = 60.dp)
        )
        PasswordField(
            uiState.password, viewModel::onPasswordChange, Modifier
                .fieldModifier()
                .padding(top = 20.dp)
        )
        RepeatPasswordField(
            uiState.repeatPassword, viewModel::onRepeatPasswordChange, Modifier
                .fieldModifier()
                .padding(top = 20.dp)
        )

        BasicButton(
            AppText.create_account,
            Modifier
                .basicButton()
                .padding(top = 40.dp)
        ) {
            viewModel.onSignUpClick(openAndPopUp)
        }

        Row(
            modifier = Modifier
                .padding(top = 8.dp)
        ) {
            Text(
                stringResource(AppText.goto_sign_in),
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
                text = stringResource(AppText.sign_in),
                fontFamily = suitFamily,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.primary_800),
                fontSize = 12.sp,
            )
        }
    }
}

@Composable
fun RequestContentPermission(viewModel: SignUpViewModel, uri: (Uri) -> Unit) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    var bitmapRemember by remember { mutableStateOf<Bitmap?>(null) }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    LaunchedEffect(imageUri) {
        if (imageUri != null) {
            uri(imageUri!!)
            viewModel.formatImage(context) { bitmap ->
                bitmapRemember = bitmap

            }
        }
    }

    Box(modifier = Modifier.padding(top = 12.dp)) {
        if(bitmapRemember != null) {
            Image(
                bitmap = bitmapRemember!!.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
                    .border(4.dp, colorResource(R.color.primary_800), CircleShape)
                    .align(Alignment.Center)
            )
        } else {
            Image(
                painter = painterResource(R.drawable.zoe),
                contentDescription = null,
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
                    .border(4.dp, colorResource(R.color.primary_800), CircleShape)
                    .align(Alignment.Center)
            )
        }

        Surface(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .clip(CircleShape)
                .clickable {
                    imageUri = null
                    launcher.launch("image/*")
                },
            color = colorResource(R.color.white)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_camera),
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
                    .padding(2.dp)
                    .border(2.dp, colorResource(R.color.white)),
                tint = colorResource(R.color.primary_800)
            )
        }

    }
}