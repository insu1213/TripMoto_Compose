package com.insu.tripmoto_compose.common.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.model.service.downloadImageFromFirebaseStorage
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun ImageLoad(modifier: Modifier = Modifier, imageName: String) {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    // 특정 이름을 가진 이미지를 다운로드한다.
    GlobalScope.launch {
        val bitmap = downloadImageFromFirebaseStorage(imageName)
        // 다운로드한 이미지를 imageBitmap에 저장한다.
        imageBitmap = bitmap?.asImageBitmap()
    }

    // 이미지를 가져오기 전에는 로딩 등을 표시할 수 있다.
    // 예시로 로딩 중에는 기본 이미지를 표시하도록 하였다.
    imageBitmap?.let { bitmap ->
        Image(
            bitmap = bitmap,
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.FillWidth
        )
    } ?: Image(
        painter = painterResource(R.drawable.zoe),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.FillWidth
    )
}