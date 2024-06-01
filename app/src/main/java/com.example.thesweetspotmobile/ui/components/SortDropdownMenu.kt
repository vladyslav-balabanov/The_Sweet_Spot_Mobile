package com.example.thesweetspotmobile.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thesweetspotmobile.ui.theme.Brown40

@Composable
fun SortDropdownMenu(
    onSortChanged: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("Сортувати за") }
    val options = listOf("За назвою", "За спаданням ціни", "За зростанням ціни")
    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(Color(0xFFEDEAE0), shape = RoundedCornerShape(50))
            .border(BorderStroke(2.dp, Color(0xFF5D4037)), shape = RoundedCornerShape(50))
            .clickable { expanded = true }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = selectedOptionText,
                color = Color(0xFF5D4037),
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Icon",
                tint = Color(0xFF5D4037)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = androidx.compose.ui.Modifier.background(Brown40)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFFEDEAE0),
                            modifier = Modifier.fillMaxWidth().padding(8.dp)
                        )
                    },
                    onClick = {
                        selectedOptionText = option
                        onSortChanged(option)
                        expanded = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Brown40)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSortDropdownMenu() {
    SortDropdownMenu()
}