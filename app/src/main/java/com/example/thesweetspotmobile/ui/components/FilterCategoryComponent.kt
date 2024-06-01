import android.provider.CalendarContract.Colors
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thesweetspotmobile.ui.theme.SweetSpotTheme


@Preview(showBackground = true)
@Composable
fun FilterCategoryComponent(
    categories: List<String> = listOf("Тістечка", "Цукерки", "Торти", "Випічка", "Печиво", "Інше"),
    updateGood: (Int) -> Unit = {},
) {
    var selectedCategoryIndex by remember { mutableStateOf(-1) }
    SweetSpotTheme(darkTheme = true, dynamicColor = false) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                selectedCategoryIndex = -1
                            }
                        )
                    }
                    .padding(16.dp)
            ) {
                categories.chunked(3).forEach { chunk ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(25.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        chunk.forEach { category ->
                            val isSelected = selectedCategoryIndex == categories.indexOf(category)
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    selectedCategoryIndex = if (isSelected) -1 else categories.indexOf(category)
                                    updateGood(selectedCategoryIndex)
                                },
                                label = {
                                    Text(
                                        text = category,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                modifier = Modifier
                                    .height(45.dp)
                                    .weight(1f),
                                shape = MaterialTheme.shapes.extraLarge,
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.tertiary,
                                    selectedLabelColor = Color.White,
                                    containerColor = MaterialTheme.colorScheme.onPrimary,
                                    labelColor = Color.Black
                                ),
                            )
                        }

                        if (chunk.size < 3) {
                            for (i in 1..(3 - chunk.size)) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
