package com.example.ticketmachinemobile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun <T> TicketMobileSelection(
    options: List<T>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit,
    showContent: (T) -> String = {it.toString()},
    expanded: Boolean,
    modifier: Modifier = Modifier
) {
    var expandedState by remember { mutableStateOf(expanded) }
    val roundedBorderShape: Shape = MaterialTheme.shapes.medium

    Box (
        modifier = modifier
            .padding(3.dp)
            .background(
                color = MaterialTheme.colors.primary,
                shape = roundedBorderShape
            )
    ) {
        // ClickableText is used as a button to trigger the dropdown
        ClickableText(
            text = AnnotatedString(showContent(selectedOption)),
            onClick = {
                expandedState = !expandedState
            },
            modifier = Modifier
                .fillMaxWidth().height(30.dp).wrapContentSize(Alignment.Center),
            // 文本居中
            style = MaterialTheme.typography.body1.copy(color = Color.White).
            copy(textAlign = TextAlign.Center)

        )

        // 下拉选单，选择查询日期
        DropdownMenu(
            expanded = expandedState,
            onDismissRequest = { expandedState = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    onOptionSelected(option)
                    expandedState = false
                }) {
                    Text(showContent(option))
                }
            }
        }
    }
}
