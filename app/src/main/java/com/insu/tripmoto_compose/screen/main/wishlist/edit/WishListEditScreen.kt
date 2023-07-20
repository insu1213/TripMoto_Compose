package com.insu.tripmoto_compose.screen.main.wishlist.edit

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.insu.tripmoto_compose.common.composable.BasicField
import com.insu.tripmoto_compose.common.composable.CardSelector
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

    LaunchedEffect(Unit) { viewModel.initialize(wishListId) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val fieldModifier = Modifier.fieldModifier()
        BasicField(AppText.title, wishList.title, viewModel::onTitleChange, fieldModifier)
        BasicField(
            AppText.description,
            wishList.description,
            viewModel::onDescriptionChange,
            fieldModifier
        )

        Spacer(modifier = Modifier.spacer())

        CardSelectors(wishList, viewModel::onFlagToggle)

        RequestContentPermission()

        Button(onClick = { viewModel.onDoneClick(popUpScreen) }) {
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
fun RequestContentPermission() {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    Column() {
        Button(onClick = {
            launcher.launch("image/*")
        }) {
            Text(text = "Pick image")
        }

        Spacer(modifier = Modifier.height(12.dp))



        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)

            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let { btm ->
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(400.dp)
                )
            }
        }

    }
}