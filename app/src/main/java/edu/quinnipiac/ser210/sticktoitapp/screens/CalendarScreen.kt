package edu.quinnipiac.ser210.sticktoitapp.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.math.ceil
import java.time.*
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen() {
    // Variables for
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    val firstDayOfMonth = currentMonth.atDay(1)
    val lastDayOfMonth = currentMonth.atEndOfMonth()
    val daysOfMonth = firstDayOfMonth.lengthOfMonth()
    val dayOfWeekOffset = firstDayOfMonth.dayOfWeek.value % 7

    val totalGridDays = dayOfWeekOffset + daysOfMonth
    val weekRows = ceil(totalGridDays / 7.0).toInt()

    Column(modifier = Modifier.padding(16.dp)) {
        // Displays current month and year
        Text(
            text = "${currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${currentMonth.year}",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // Creates month navigation arrows
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
                Text("<")
            }
            IconButton(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
                Text(">")
            }
        }

        // Creates day of week headers
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            DayOfWeek.values().forEach {
                Text(
                    it.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Calendar Grid
        Column {
            for (week in 0 until weekRows) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (day in 0..6) {
                        // Calculates date number for this cell
                        val dayNumber = week * 7 + day - dayOfWeekOffset + 1
                        val date = if (dayNumber in 1..daysOfMonth) currentMonth.atDay(dayNumber) else null

                        // Renders each cell of the calendar
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(2.dp)
                                .background(
                                    if (date == selectedDate) MaterialTheme.colorScheme.primary else Color.Transparent
                                )
                                .clickable(enabled = date != null) {
                                    if (date != null) {
                                        selectedDate = date
                                        // Todo: Create navigation for going back to homescreen
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = date?.dayOfMonth?.toString() ?: "")
                        }
                    }
                }
            }
        }

        // // Spacer and display for selected date
        Spacer(modifier = Modifier.height(16.dp))
        Text("Selected date: $selectedDate", style = MaterialTheme.typography.bodyLarge)
    }
}
