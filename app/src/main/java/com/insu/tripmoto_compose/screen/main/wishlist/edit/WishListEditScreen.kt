package com.insu.tripmoto_compose.screen.main.wishlist.edit

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.common.composable.BasicButton
import com.insu.tripmoto_compose.common.composable.CardSelector
import com.insu.tripmoto_compose.common.composable.LimitTextField
import com.insu.tripmoto_compose.common.composable.MainTitleText
import com.insu.tripmoto_compose.common.ext.basicButton
import com.insu.tripmoto_compose.common.ext.card
import com.insu.tripmoto_compose.common.ext.fieldModifier
import com.insu.tripmoto_compose.common.ext.spacer
import com.insu.tripmoto_compose.model.WishList
import com.insu.tripmoto_compose.R.string as AppText


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
            MainTitleText(
                modifier = modifier.align(Alignment.CenterStart),
                text = AppText.wishlist
            )
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

        Button(
            modifier = Modifier
                .basicButton()
                .padding(top = 44.dp),
            onClick = {
                if(addBtnState) {
                    addBtnState = false
                    viewModel.onDoneClick() {
                        addBtnState = true
                        popUpScreen()
                    }
                }
            }
        ) {
            if(addBtnState) {
                Text("Post")
            } else {
                CircularProgressIndicator()
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
    val bitmapRemember = remember { mutableStateOf<Bitmap?>(null) }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    LaunchedEffect(imageUri) {
        if (imageUri != null) {
            uri(imageUri!!)
        }
    }

    Column() {
        Button(onClick = {
            launcher.launch("image/*")
        }) {
            Text(text = "Pick image")
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (imageUri != null) {
            bitmapRemember.value?.let { btm ->
                viewModel.uploadImage(context) { bitmap ->
                    bitmapRemember.value = bitmap
                }
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
            }
        }
    }
}