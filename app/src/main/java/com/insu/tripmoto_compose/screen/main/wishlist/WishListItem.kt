package com.insu.tripmoto_compose.screen.main.wishlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.common.composable.DropdownContextMenu
import com.insu.tripmoto_compose.common.ext.contextMenu
import com.insu.tripmoto_compose.model.WishList
import com.insu.tripmoto_compose.suitFamily
import com.insu.tripmoto_compose.R.drawable as AppIcon

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WishListItem(
    wishList: WishList,
    options: List<String>,
    onCheckChange: () -> Unit,
    onActionClick: (String) -> Unit
) {
    //val viewModel = ImageLoadViewModel()
    //val imageBitmapState: State<ImageBitmap?>? = viewModel.getImageBitmap(wishList.id)

    Card(
        shape = RoundedCornerShape(6.dp),
        backgroundColor = colorResource(R.color.gray_1),
        modifier = Modifier
            .padding(5.dp),
        elevation = 2.dp
    ) {
        Column() {
//            if(wishList.isImage) {
//                if (imageBitmapState != null) {
//                    imageBitmapState.value?.let { imageBitmap ->
//                        Image(
//                            bitmap = imageBitmap,
//                            contentDescription = null,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(100.dp),
//                            contentScale = ContentScale.FillWidth
//                        )
//                    } ?: Image(
//                        painter = painterResource(R.drawable.zoe),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(100.dp),
//                        contentScale = ContentScale.FillWidth
//                    )
//                }
//            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(8.5F)) {
                    Text(
                        text = wishList.title,
                        modifier = Modifier
                            .padding(top = 4.dp, start = 4.dp),
                        color = colorResource(R.color.black),
                        fontSize = 14.sp,
                        fontFamily = suitFamily,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                    )

                    Text(
                        text = wishList.description,
                        modifier = Modifier
                            .padding(top = 4.dp, bottom = 4.dp, start = 4.dp),
                        color = colorResource(R.color.black),
                        fontSize = 12.sp,
                        fontFamily = suitFamily,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start,
                    )
                }
                Column(modifier = Modifier.weight(1.5F)) {
                    DropdownContextMenu(options, Modifier.contextMenu(), onActionClick) {}
                    if (wishList.flag) {
                        Icon(
                            painter = painterResource(AppIcon.ic_star),
                            tint = Color(0xFFFFCD3C),
                            contentDescription = "Star"
                        )
                    }
                }
            }

//            Checkbox(
//                checked = wishList.completed,
//                onCheckedChange = { onCheckChange() },
//                modifier = Modifier.padding(8.dp, 0.dp)
//            )

        }
    }
}