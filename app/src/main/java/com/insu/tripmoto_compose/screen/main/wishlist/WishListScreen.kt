package com.insu.tripmoto_compose.screen.main.wishlist

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.BackOnPressed
import com.insu.tripmoto_compose.common.composable.MainTitleText
import com.insu.tripmoto_compose.rememberAppState
import com.insu.tripmoto_compose.screen.fore.ForeViewModel
import com.insu.tripmoto_compose.suitFamily
import dagger.hilt.android.components.ActivityComponent
import com.insu.tripmoto_compose.R.string as AppText
import com.insu.tripmoto_compose.R.color as AppColor
import com.insu.tripmoto_compose.R.drawable as AppIcon

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@ExperimentalMaterialApi
fun WishListScreen(
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WishListViewModel = hiltViewModel()
) {
    /*
    * 1. Delay Loading 창 만들기: 지연시간 때문에 앱 State가 모두 멈추게 되는 현상 발생.
    *
    * */


    BackOnPressed()

    val listState = rememberLazyStaggeredGridState()
    //initialFirstVisibleItemIndex = 99 해당 위치가 마지막으로 보이도록 설정

    val wishList = viewModel.wishList.collectAsStateWithLifecycle(emptyList())
    val options by viewModel.options
    var wishListIsEmptyCheck by remember { mutableStateOf(false) }

    LaunchedEffect(wishList.value) {
        wishListIsEmptyCheck = wishList.value.isEmpty()
    }

    if(wishListIsEmptyCheck) {
        Box(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(AppText.trip_empty),
                color = colorResource(AppColor.gray_5),
                fontFamily = suitFamily,
                fontWeight = FontWeight.Normal,
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 68.dp)
        ) {
            Box(modifier = modifier.fillMaxWidth()) {
                MainTitleText(
                    modifier = modifier.align(Alignment.CenterStart),
                    text = AppText.wishlist
                )

                Text(
                    modifier = modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            viewModel.onAddClick(openScreen)
                        },
                    text = "추가하기",
                    color = colorResource(AppColor.primary_800)
                )
            }

            Spacer(modifier = Modifier.padding(bottom = 8.dp))

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(150.dp),
                state = listState,
                verticalItemSpacing = 2.dp,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                content = {
                    items(wishList.value, key = { it.id }) { wishListItem ->
                        WishListItem(
                            wishList = wishListItem,
                            options = options,
                            onCheckChange = { viewModel.onWishListCheckChange(wishListItem) },
                            onActionClick = { action ->
                                viewModel.onWishListActionClick(
                                    openScreen,
                                    wishListItem,
                                    action
                                )
                            }
                        )
                    }
                }
            )
        }
    }

    LaunchedEffect(viewModel) { viewModel.loadWishListOptions() }
}