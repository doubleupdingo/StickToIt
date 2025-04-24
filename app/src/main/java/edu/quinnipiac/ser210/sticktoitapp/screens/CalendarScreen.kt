package edu.quinnipiac.ser210.sticktoitapp.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.quinnipiac.ser210.sticktoitapp.viewmodel.DateViewModel
import androidx.compose.runtime.collectAsState
import kotlin.math.ceil

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(navController: NavController, viewModel: DateViewModel = viewModel()) {
    val selectedDate by viewModel.selectedDate.collectAsState()
    val currentMonth by viewModel.currentMonth.collectAsState()

    val firstDayOfMonth = currentMonth.atDay(1)
    val daysOfMonth = currentMonth.lengthOfMonth()
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
            IconButton(onClick = {
                viewModel.changeMonth(currentMonth.minusMonths(1))
            }) {
                Text("<")
            }
            IconButton(onClick = {
                viewModel.changeMonth(currentMonth.plusMonths(1))
            }) {
                Text(">")
            }
        }

        // Creates day of week headers
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            val daysOfWeek = listOf(
                DayOfWeek.SUNDAY,
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY,
                DayOfWeek.SATURDAY
            )
            daysOfWeek.forEach {
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
                        val dayNumber = week * 7 + day + 1
                        val date = if (dayNumber > dayOfWeekOffset && dayNumber <= daysOfMonth + dayOfWeekOffset) currentMonth.atDay(dayNumber - dayOfWeekOffset) else null

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
                                        viewModel.onDateSelected(date) // Update the date in the viewmodel.
                                        navController.popBackStack() //navigate back to homescreen.
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = date?.dayOfMonth?.toString() ?: "",
                                color = if (date == selectedDate) Color.White else Color.Unspecified
                            )
                        }
                    }
                }
            }
        }
    }
}