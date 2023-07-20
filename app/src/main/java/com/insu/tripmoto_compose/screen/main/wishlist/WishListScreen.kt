package com.insu.tripmoto_compose.screen.main.wishlist

import android.annotation.SuppressLint
import android.graphics.Color
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@ExperimentalMaterialApi
fun WishListScreen(
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WishListViewModel = hiltViewModel()
) {

    val listState = rememberLazyGridState(
        //initialFirstVisibleItemIndex = 99 해당 위치가 마지막으로 보이도록 설정
    )
    val wishList = viewModel.wishList.collectAsStateWithLifecycle(emptyList())
    val options by viewModel.options

    Column(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 12.dp)
    ) {
        Row() {
            MainTitleText(text = AppText.wishlist)
            TextButton(
                modifier = modifier,
                onClick =  {viewModel.onAddClick(openScreen) },
            ) {
                Text("추가하기")
            }
        }


        Spacer(modifier = Modifier.padding(bottom = 8.dp))

        LazyVerticalGrid(
            state = listState,
            columns = GridCells.Adaptive(150.dp),
            content = {
                items(wishList.value, key = { it.id }) { wishListItem ->
                    WishListItem(
                        wishList = wishListItem,
                        options = options,
                        onCheckChange = { viewModel.onWishListCheckChange(wishListItem) },
                        onActionClick = { action -> viewModel.onWishListActionClick(openScreen, wishListItem, action)}
                    )
                }
            }
        )
    }

    LaunchedEffect(viewModel) { viewModel.loadWishListOptions() }
}