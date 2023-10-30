package com.insu.tripmoto_compose.screen.main.wishlist.edit

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.BasicButton
import com.insu.tripmoto_compose.common.composable.CardSelector
import com.insu.tripmoto_compose.common.composable.LimitTextField
import com.insu.tripmoto_compose.common.composable.MainTitleText
import com.insu.tripmoto_compose.common.ext.basicButton
import com.insu.tripmoto_compose.common.ext.card
import com.insu.tripmoto_compose.common.ext.fieldModifier
import com.insu.tripmoto_compose.common.ext.spacer
import com.insu.tripmoto_compose.model.WishList
import com.insu.tripmoto_compose.suitFamily
import com.insu.tripmoto_compose.R.string as AppText
import com.insu.tripmoto_compose.R.color as AppColor
import com.insu.tripmoto_compose.R.drawable as AppIcon


@Composable
@ExperimentalMaterialApi
fun WishListEditScreen(
    popUpScreen: () -> Unit,
    wishListId: String,
    modifier: Modifier = Modifier,
    viewModel: WishListEditViewModel = hiltViewModel()
) {
    val wishList by viewModel.wishList
    val contentResolver = LocalContext.current.contentResolver
    var addBtnState by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) { viewModel.initialize(wishListId) }

    Column(
        modifier = modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 68.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val fieldModifier = Modifier.fieldModifier()
        Box(modifier = modifier.fillMaxWidth()) {
            Row(modifier = modifier.fillMaxSize()) {
                Icon(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .clickable {
                            popUpScreen()
                        },
                    painter = painterResource(id = R.drawable.ic_left_arrow),
                    contentDescription = ""
                )
                MainTitleText(
                    text = AppText.create_wishlist
                )
            }
        }

        LimitTextField(
            15,
            AppText.title,
            wishList.title,
            viewModel::onTitleChange,
            fieldModifier
                .padding(top = 8.dp)
        )
        LimitTextField(
            100,
            AppText.description,
            wishList.description,
            viewModel::onDescriptionChange,
            fieldModifier.padding(top = 12.dp)
        )

        Spacer(modifier = Modifier.spacer())

        CardSelectors(wishList, viewModel::onFlagToggle)

        RequestContentPermission(viewModel) { uri ->
            viewModel.onImageResourceChange(uri)
        }

//        Button(
//            modifier = Modifier
//                .basicButton()
//                .padding(top = 44.dp),
//            onClick = {
//                if(addBtnState) {
//                    addBtnState = false
//                    viewModel.onDoneClick() {
//                        addBtnState = true
//                        popUpScreen()
//                    }
//                }
//            }
//        ) {
//            if(addBtnState) {
//                Text("Post")
//            } else {
//                CircularProgressIndicator()
//            }
//        }

        Button(
            onClick = {
                if(addBtnState) {
                    addBtnState = false
                    viewModel.onDoneClick() {
                        addBtnState = true
                        popUpScreen()
                    }
                }
            },
            modifier = modifier
                .fillMaxWidth()
                .height(40.dp),
            shape = RoundedCornerShape(24.dp),
            colors =
            ButtonDefaults.buttonColors(
                backgroundColor = colorResource(R.color.primary_800),
                contentColor = MaterialTheme.colors.onPrimary
            )
        ) {
            if(addBtnState) {
                Text(
                    text = stringResource(AppText.create_wishlist),
                    fontSize = 16.sp,
                    fontFamily = suitFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.white)
                )
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = colorResource(AppColor.white)
                )
            }
        }
    }
}

@Composable
@ExperimentalMaterialApi
private fun CardSelectors(
    wishList: WishList,
    onFlagToggle: (String) -> Unit
) {
    val flagSelection = EditFlagOption.getByCheckedState(wishList.flag).name
    CardSelector(
        AppText.flag,
        EditFlagOption.getOptions(),
        flagSelection,
        Modifier.card()
    ) { newValue
        ->
        onFlagToggle(newValue)
    }
}


@Composable
fun RequestContentPermission(viewModel: WishListEditViewModel, uri: (Uri) -> Unit) {
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


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if(bitmapRemember == null) {
                "이미지 없음"
            } else {
                "이미지 업로드 됨"
            }
            ,
            fontSize = 13.sp,
            fontFamily = suitFamily,
            fontWeight = FontWeight.SemiBold,
            color = if(bitmapRemember == null) {
                colorResource(AppColor.black)
            } else {
                colorResource(AppColor.green)
            }
        )

        if(bitmapRemember != null) {
            Icon(
                painter = painterResource(AppIcon.ic_check),
                contentDescription = null,
                tint = colorResource(id = AppColor.green)
            )
        }

        Text(
            modifier = Modifier
                .padding(start = 4.dp)
                .clickable {
                    imageUri = null
                    launcher.launch("image/*")
                },
            text = stringResource(AppText.upload_image),
            color = colorResource(id = AppColor.primary_800),
            fontSize = 14.sp,
            fontFamily = suitFamily,
            fontWeight = FontWeight.SemiBold,
        )

        if(bitmapRemember != null) {
            Text(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable {
                        viewModel.clearImageUri()
                        bitmapRemember = null
                    },
                text = "삭제",
                color = colorResource(id = AppColor.red),
                fontSize = 14.sp,
                fontFamily = suitFamily,
                fontWeight = FontWeight.SemiBold,
            )
        }


        Spacer(modifier = Modifier.height(12.dp))

//        if(bitmapRemember != null) {
//            Image(
//                bitmap = bitmapRemember!!.asImageBitmap(),
//                contentDescription = null,
//                modifier = Modifier.size(400.dp)
//            )
//        }
    }
}