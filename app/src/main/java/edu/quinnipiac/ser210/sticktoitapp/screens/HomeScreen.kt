package edu.quinnipiac.ser210.sticktoitapp.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import edu.quinnipiac.ser210.sticktoitapp.data.Event
import edu.quinnipiac.ser210.sticktoitapp.data.Task
import edu.quinnipiac.ser210.sticktoitapp.viewmodel.DateViewModel
import edu.quinnipiac.ser210.sticktoitapp.viewmodel.SettingsViewModel
import edu.quinnipiac.ser210.sticktoitapp.viewmodel.TaskEventViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: DateViewModel,
    taskEventViewModel: TaskEventViewModel,
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val selectedDate by viewModel.selectedDate.collectAsState()
    val displayDate = selectedDate

    val militaryTimeEnabled by settingsViewModel.militaryTimeEnabled.collectAsState()

    // Changed to conditional formatting
    val timeFormatter = if (militaryTimeEnabled) {
        DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
    } else {
        DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault())
    }
    val currentTime = remember {
        LocalTime.now()
    }.format(timeFormatter)

    val coroutineScope = rememberCoroutineScope()

    var refreshList by remember { mutableStateOf(false) }

    // Event and task lists
    var events by remember { mutableStateOf<List<Event>>(emptyList()) }
    var tasks by remember { mutableStateOf<List<Task>>(emptyList()) }

    // Variables to hold selected events/tasks & state of the dialog
    var selectedEvent by remember { mutableStateOf<Event?>(null) }
    var selectedTask by remember { mutableStateOf<Task?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    // Runs when a new date is selected
    LaunchedEffect(key1 = selectedDate, key2 = refreshList) {
        coroutineScope.launch {
            // Defaults to current date if selected date is somehow null
            val tempSelectedDate = selectedDate ?: LocalDate.now()

            // Gets the events and tasks for the selected date
            events = taskEventViewModel.getEventsByDate(tempSelectedDate)
            tasks = taskEventViewModel.getTasksByDate(tempSelectedDate)
            refreshList = false
        }
    }

    // Show dialog if an event or task is selected
    if (showDialog) {
        EventTaskDialog(
            event = selectedEvent,
            task = selectedTask,
            onDismiss = {
                showDialog = false
                selectedEvent = null
                selectedTask = null
            },
            onComplete = {
                showDialog = false
                selectedEvent = null
                selectedTask = null
                refreshList = true
            },
            taskEventViewModel = taskEventViewModel
        )
    }

    // Column for all UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Date display
        Text(
            text = displayDate!!.format(DateTimeFormatter.ofPattern("EEEE, MMMM d',' yyyy")),
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

        // Time display
        Text(
            text = currentTime,
            fontSize = 28.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )

        // Event & Task lists
        SectionTitle("Today's Events:")
        EventListSection(
            items = events,
            onEventClicked = { event ->
                selectedEvent = event
                showDialog = true
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        SectionTitle("Today's Tasks:")
        TaskListSection(
            items = tasks,
            onTaskClicked = { task ->
                selectedTask = task
                showDialog = true
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Previous & Next day buttons at bottom of screen
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    viewModel.onDateSelected(displayDate.minusDays(1))
                }) {
                    Text("<-- Previous Day")
                }
                Button(onClick = {
                    viewModel.onDateSelected(displayDate.plusDays(1))
                }) {
                    Text("Next Day -->")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// Formatting for the event & task titles
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

// Formatting for the event list (lazy column)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventListSection(items: List<Event>, onEventClicked: (Event) -> Unit) {
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
                //Added clickable
                Text(
                    text = item.title,
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { onEventClicked(item) }
                )
            }
        }
    }
}

// Formatting for the task list (lazy column)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskListSection(items: List<Task>, onTaskClicked: (Task) -> Unit) {
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
                //added clickable
                Text(
                    text = item.title,
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { onTaskClicked(item) }
                )
            }
        }
    }
}

// Function to handle dialog box popup when a task or event is selected
// Can view details, as well as mark it as complete (aka delete it)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventTaskDialog(
    event: Event? = null,
    task: Task? = null,
    onDismiss: () -> Unit,
    onComplete: () -> Unit,
    taskEventViewModel: TaskEventViewModel
) {
    if (event == null && task == null) {
        onDismiss() // Dismiss if no event or task present (essentially never opens)
        return
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = event?.title ?: task?.title ?: "Details") },
        text = {
            Column {
                if (event != null) {
                    Text("Start Time: ${event.startTime.format(DateTimeFormatter.ofPattern("h:mm a"))}")
                    Text("End Time: ${event.endTime.format(DateTimeFormatter.ofPattern("h:mm a"))}")
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Description: ${event.description}")
                } else if (task != null) {
                    Text("Description: ${task.description}")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (event != null) {
                    taskEventViewModel.deleteEvent(event)
                } else if (task != null) {
                    taskEventViewModel.deleteTask(task)
                }
                onComplete()
                onDismiss()
            }) {
                Text("Mark as Complete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}