package com.insu.tripmoto_compose.common.composable

import androidx.annotation.StringRes
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.insu.tripmoto_compose.common.ext.dropdownSelector

@Composable
@ExperimentalMaterialApi
fun CardSelector(
    @StringRes label: Int,
    options: List<String>,
    selection: String,
    modifier: Modifier,
    onNewValue: (String) -> Unit
) {
    Card(backgroundColor = MaterialTheme.colors.onPrimary, modifier = modifier) {
        DropdownSelector(label, options, selection, Modifier.dropdownSelector(), onNewValue)
    }
}
