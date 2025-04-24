package edu.quinnipiac.ser210.sticktoitapp.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navController: NavHostController) {
    // Current Date & Time
    var selectedDate by remember {
        mutableStateOf(LocalDate.of(2025, 4, 17))
    }
    val currentTime = remember {
        LocalTime.now().format(DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault()))
    }

    // Event & Task lists (temporary filler entries)
    val events = listOf(
        "Event 1 [2:00 - 2:30pm]",
        "Event 2 [3:00 - 5:30pm]",
        "Event 3 [7:30 - 9:00pm]"
    )
    val tasks = listOf("Task 1", "Task 2", "Task 3")

    // Column for all UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Date
        Text(
            text = selectedDate.format(DateTimeFormatter.ofPattern("EEEE, MMMM d',' yyyy")),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
        )

        // Horizontal line divider between Date & Time
        HorizontalDivider(
            modifier = Modifier.padding(2.dp),
            thickness = 4.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.30f)
        )

        // Time
        Text(
            text = currentTime,
            fontSize = 28.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )

        // Event & Task lists
        SectionTitle("Today's Events:")
        ListSection(items = events)
        Spacer(modifier = Modifier.height(16.dp))

        SectionTitle("Today's Tasks:")
        ListSection(items = tasks)
        Spacer(modifier = Modifier.height(16.dp))

        // Previous & Next day buttons
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    selectedDate = selectedDate.minusDays(1)
                }) {
                    Text("<-- Previous Day")
                }
                Button(onClick = {
                    selectedDate = selectedDate.plusDays(1)
                }) {
                    Text("Next Day -->")
                }
            }
        }
    }
}

// Formatting for event & task titles
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 2.dp)
    )
}

// Formatting for the event & task lists (lazycolumns)
@Composable
fun ListSection(items: List<String>) {
    Surface(
        tonalElevation = 4.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 180.dp, max = 180.dp)
    ) {
        LazyColumn(
            modifier = Modifier.padding(8.dp)
        ) {
            items(items) { item ->
                Text(text = item, modifier = Modifier.padding(4.dp))
            }
        }
    }
}
