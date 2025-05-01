package edu.quinnipiac.ser210.sticktoitapp.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.quinnipiac.ser210.sticktoitapp.data.Event
import edu.quinnipiac.ser210.sticktoitapp.data.Task
import edu.quinnipiac.ser210.sticktoitapp.navigation.StickScreens
import edu.quinnipiac.ser210.sticktoitapp.ui.theme.StickToItAppTheme
import edu.quinnipiac.ser210.sticktoitapp.viewmodel.TaskEventViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateEntryScreen(
    navController: NavController,
    taskEventViewModel: TaskEventViewModel
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var entryType by remember { mutableStateOf("Event") }
    val types = listOf("Event", "Task")

    var eventName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var startTime by remember { mutableStateOf<LocalTime?>(null) }
    var endTime by remember { mutableStateOf<LocalTime?>(null) }
    var date by remember { mutableStateOf<LocalDate?>(null) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },

        ) { paddingValues ->
        // Column for all UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),//Add the padding here
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Title, formatted similarly to home screen
            Text("Create New Entry", fontSize = 28.sp, fontWeight = FontWeight.Bold)

            HorizontalDivider(
                modifier = Modifier.padding(2.dp),
                thickness = 4.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.30f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Dropdown to select task or event
            Text("Are You Creating a Task or an Event?", fontWeight = FontWeight.SemiBold)
            EntryTypeDropdown(types, entryType) {
                entryType = it
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Name text input field
            Text(
                "Task/Event Name:",
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = eventName,
                onValueChange = { eventName = it },
                label = { Text("Enter a name here!") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons for event start and end times
            Text("Start & End Times (Event Only)", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TimeButton(
                    text = startTime?.format(DateTimeFormatter.ofPattern("h:mm a"))
                        ?: "Set Start Time",
                    enabled = entryType == "Event"
                ) {
                    pickTime(context) {
                        startTime = it
                    }
                }
                TimeButton(
                    text = endTime?.format(DateTimeFormatter.ofPattern("h:mm a")) ?: "Set End Time",
                    enabled = entryType == "Event"
                ) {
                    pickTime(context) {
                        endTime = it
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Button for entry date
            Text("Task/Event Date", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            Button(onClick = {
                pickDate(context) {
                    date = it
                }
            }) {
                Text(text = date?.format(DateTimeFormatter.ofPattern("MMM d, yyyy")) ?: "Set Date")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Optional description text input field
            Text(
                "Task/Event Description (Optional):",
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                placeholder = { Text("Enter a description here!") }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Button at bottom to save entry to calendar
            Column(
                modifier = Modifier
                    .fillMaxWidth(),//removed paddingValues and .fillMaxSize()
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch { // Displays snackbar messages if required info is missing
                            if (eventName.isEmpty()) {
                                snackbarHostState.showSnackbar("Please enter a name!")
                                return@launch
                            }
                            if (date == null) {
                                snackbarHostState.showSnackbar("Please select a date!")
                                return@launch
                            }
                            if (entryType == "Event" && (startTime == null || endTime == null)) {
                                snackbarHostState.showSnackbar("Please select a start and end time!")
                                return@launch
                            }
                            if (entryType == "Event") {
                                val event = Event(
                                    title = eventName,
                                    description = description,
                                    date = date!!,
                                    startTime = startTime!!,
                                    endTime = endTime!!
                                )
                                taskEventViewModel.insertEvent(event)
                                Toast.makeText(context, "Event Saved!", Toast.LENGTH_SHORT).show()
                            } else {
                                val task = Task(
                                    title = eventName,
                                    description = description,
                                    date = date!!
                                )
                                taskEventViewModel.insertTask(task)
                                Toast.makeText(context, "Task Saved!", Toast.LENGTH_SHORT).show()
                            }
                            navController.navigate(StickScreens.HomeScreen.name)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Task/Event to Calendar")
                }
            }
        }
    }
}

// Rest of your code (EntryTypeDropdown, TimeButton, pickTime, pickDate)
@Composable
fun EntryTypeDropdown(
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
            Text(selected)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { type ->
                DropdownMenuItem(text = { Text(type) }, onClick = {
                    onSelected(type)
                    expanded = false
                })
            }
        }
    }
}

@Composable
fun TimeButton(text: String, enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Text(text)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun pickTime(context: Context, onTimeSelected: (LocalTime) -> Unit) {
    val calendar = Calendar.getInstance()
    TimePickerDialog(
        context,
        { _, hour, minute -> onTimeSelected(LocalTime.of(hour, minute)) },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    ).show()
}

@RequiresApi(Build.VERSION_CODES.O)
fun pickDate(context: Context, onDateSelected: (LocalDate) -> Unit) {
    val calendar = Calendar.getInstance()
    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth -> onDateSelected(LocalDate.of(year, month + 1, dayOfMonth)) },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}