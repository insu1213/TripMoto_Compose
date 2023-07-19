package com.insu.tripmoto_compose.screen.main

import android.graphics.Color
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.MainTitleText
import com.insu.tripmoto_compose.screen.fore.ForeViewModel
import com.insu.tripmoto_compose.suitFamily
import com.insu.tripmoto_compose.R.string as AppText
import com.insu.tripmoto_compose.R.color as AppColor
import com.insu.tripmoto_compose.R.drawable as AppIcon

@Composable
fun WishListScreen(
    modifier: Modifier = Modifier,
    viewModel: ForeViewModel = hiltViewModel()
) {

    val listState = rememberLazyGridState(
        //initialFirstVisibleItemIndex = 99 해당 위치가 마지막으로 보이도록 설정
    )
    Column(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 12.dp)
    ) {
        MainTitleText(text = AppText.wishlist)

        Spacer(modifier = Modifier.padding(bottom = 8.dp))

        LazyVerticalGrid(
            state = listState,
            columns = GridCells.Adaptive(150.dp),
            content = {
                items(100) { i ->
                    Card(
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .padding(10.dp)
                            .background(colorResource(AppColor.gray_1)),
                        elevation = 8.dp
                    ) {
                        Column() {
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp),
                                painter = painterResource(AppIcon.zoe),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth
                            )
                            Text(
                                text = "Item Title $i",
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp, start = 4.dp),
                                color = colorResource(R.color.black),
                                fontSize = 14.sp,
                                fontFamily = suitFamily,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Start,
                            )

                            Text(
                                text = "Item Description $i",
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp, bottom = 4.dp, start = 4.dp),
                                color = colorResource(R.color.black),
                                fontSize = 12.sp,
                                fontFamily = suitFamily,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Start,
                            )
                        }
                    }
                }
            }
        )
    }

}