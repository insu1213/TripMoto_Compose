package com.insu.tripmoto_compose.common.ext

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.basicButton(): Modifier {
    return this.fillMaxWidth().padding(16.dp, 8.dp)
}

fun Modifier.fieldModifier(): Modifier {
    return this.fillMaxWidth().padding(16.dp, 4.dp)
}

fun Modifier.spacer(): Modifier {
    return this.fillMaxWidth().padding(12.dp)
}

fun Modifier.dropdownSelector(): Modifier {
    return this.fillMaxWidth()
}

fun Modifier.card(): Modifier {
    return this.padding(16.dp, 0.dp, 16.dp, 8.dp)
}

fun Modifier.contextMenu(): Modifier {
    return this.wrapContentWidth()
}
