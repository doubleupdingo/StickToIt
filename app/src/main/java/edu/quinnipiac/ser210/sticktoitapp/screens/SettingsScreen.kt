package edu.quinnipiac.ser210.sticktoitapp.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.quinnipiac.ser210.sticktoitapp.viewmodel.TaskEventViewModel
import kotlinx.coroutines.launch

// Composable function for settings screen
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsScreen(
    navController: NavController,
    taskEventViewModel: TaskEventViewModel
) {
    val coroutineScope = rememberCoroutineScope()

    var darkModeEnabled by remember { mutableStateOf(false) }
    var militaryTimeEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Rounded border for settings toggles
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(
                    width = 2.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
        ) {
            // Toggle button for switching to dark mode
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Dark Mode", style = MaterialTheme.typography.bodyLarge)
                Switch(
                    checked = darkModeEnabled,
                    onCheckedChange = { darkModeEnabled = it }
                )
            }

            // Toggle button for switching to military time
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Military Time", style = MaterialTheme.typography.bodyLarge)
                Switch(
                    checked = militaryTimeEnabled,
                    onCheckedChange = { militaryTimeEnabled = it }
                )
            }
        }

        // Button for clearing task data
        Button(
            onClick = {
                coroutineScope.launch {
                    taskEventViewModel.clearAllData()
                }
            },
            modifier = Modifier.padding(top = 32.dp)
        ) {
            Text("Clear Data")
        }
    }
}