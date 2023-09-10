package com.insu.tripmoto_compose.screen.main.map.edit

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.graphics.toColorInt
import androidx.core.graphics.toColorLong
import com.insu.tripmoto_compose.R.color as AppColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ColorPickerDialog(
    initialColor: String,
    colors: List<String>,
    onChoice: (String) -> Unit
) {
    var color by remember(initialColor) { mutableStateOf(initialColor) }
    var hexTextColor by remember(initialColor) {
        mutableStateOf(initialColor.toColor(Color.White).contrastColor())
    }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current


    val colorRendered by animateColorAsState(
        targetValue = "#$color".toColor(Color.White),
        label = "color-picker-animation",
        finishedListener = {
            hexTextColor = it.contrastColor()
        }
    )

    val onDismissRequest = {
        if (Patterns.color.matches("#$color")) {
            onChoice("#$color")
        }
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = Modifier.width(84.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
            ) {
                BasicTextField(
                    value = color,
                    onValueChange = {
                        color = it
                    },
                    textStyle = MaterialTheme.typography.headlineSmall
                        .copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            color = hexTextColor,
                            fontSize = 14.sp
                        ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .background(colorRendered)
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(colors) {
                            Button(
                                onClick = { color = it },
                                shape = CircleShape,
                                modifier = Modifier.requiredSize(28.dp),
                                contentPadding = PaddingValues(1.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color("#$it".toColorInt())
                                ),
                                border = if (it == color)
                                    BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface)
                                else
                                    null,
                                content = {}
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    TextButton(onClick = onDismissRequest, modifier = Modifier.align(Alignment.End)) {
                        Text(text = "완료", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    }
}