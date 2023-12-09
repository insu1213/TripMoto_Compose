package com.insu.tripmoto_compose.screen.member.permission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.insu.tripmoto_compose.common.composable.BasicButton
import com.insu.tripmoto_compose.screen.main.map.edit.EditMarkerViewModel
import com.insu.tripmoto_compose.R.string as AppText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDialog(
    onDismiss: (String) -> Unit,
    viewModel: PermissionViewModel = hiltViewModel()
) {
    Dialog(onDismissRequest = {
        onDismiss("")
    }) {
        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = 8.dp
        ) {
            val radioOptions = listOf("공동 관리자", "수정자", "그룹원")
            var selectedOption by remember { mutableStateOf(radioOptions[0]) }

            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                radioOptions.forEach { fruitName ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = (fruitName == selectedOption),
                            onClick = { selectedOption = fruitName }
                        )
                        Text(
                            text = fruitName,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.Magenta)
                        ) {
                            append(selectedOption)
                        }
                        append(" 역할이 적용됩니다.")
                    }
                )
                BasicButton(text = AppText.done, modifier = Modifier.padding(top = 12.dp)) {
                    onDismiss(selectedOption)
                }
            }
        }
    }
}