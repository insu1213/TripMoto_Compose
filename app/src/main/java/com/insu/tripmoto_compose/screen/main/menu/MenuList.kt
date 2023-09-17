package com.insu.tripmoto_compose.screen.main.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insu.tripmoto_compose.R
import com.insu.tripmoto_compose.suitFamily

@Composable
fun MenuList(
    menu: Menu,
    callback: (String) -> Unit
) {
    val configuration = LocalConfiguration.current

    Box(
        modifier = Modifier
            .width(40.dp)
            .clickable {
                callback(menu.text)
            }
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .size(36.dp),
                painter = painterResource(menu.paint),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = menu.text,
                fontSize = 13.sp,
                fontFamily = suitFamily,
                fontWeight = FontWeight.Normal,
            )
        }
    }
}