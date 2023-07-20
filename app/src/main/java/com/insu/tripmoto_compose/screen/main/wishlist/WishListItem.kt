package com.insu.tripmoto_compose.screen.main.wishlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
    Card(
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .padding(10.dp)
            .background(colorResource(R.color.gray_1)),
        elevation = 8.dp
    ) {
        Column() {
            wishList.imageRes?.let { painterResource(it) }?.let {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    painter = it,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
            }
            Text(
                text = wishList.title,
                modifier = Modifier
                    .fillMaxWidth()
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
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 4.dp, start = 4.dp),
                color = colorResource(R.color.black),
                fontSize = 12.sp,
                fontFamily = suitFamily,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
            )

            Checkbox(
                checked = wishList.completed,
                onCheckedChange = { onCheckChange() },
                modifier = Modifier.padding(8.dp, 0.dp)
            )
            if (wishList.flag) {
                Icon(
                    painter = painterResource(AppIcon.ic_flag),
                    tint = Color.Yellow,
                    contentDescription = "Flag"
                )
            }
            DropdownContextMenu(options, Modifier.contextMenu(), onActionClick)
        }
    }
}