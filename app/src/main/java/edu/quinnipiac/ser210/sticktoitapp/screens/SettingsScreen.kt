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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.quinnipiac.ser210.sticktoitapp.viewmodel.SettingsViewModel
import edu.quinnipiac.ser210.sticktoitapp.viewmodel.TaskEventViewModel
import kotlinx.coroutines.launch

// Composable function for settings screen
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsScreen(
    navController: NavController,
    taskEventViewModel: TaskEventViewModel,
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val darkModeEnabled by settingsViewModel.darkModeEnabled.collectAsState()
    val militaryTimeEnabled by settingsViewModel.militaryTimeEnabled.collectAsState()

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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Dark Mode", style = MaterialTheme.typography.bodyLarge)
                Switch(
                    checked = darkModeEnabled,
                    onCheckedChange = { settingsViewModel.setDarkModeEnabled(it) }
                )
            }

            // Toggle button for switching to military time
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Military Time on Home Screen", style = MaterialTheme.typography.bodyLarge)
                Switch(
                    checked = militaryTimeEnabled,
                    onCheckedChange = { settingsViewModel.setMilitaryTimeEnabled(it) }
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

        Text(
            text = "About StickToIt:",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 32.dp, bottom = 8.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = "StickToIt is an Android app that allows you to view the tasks and events you have for the given day. You can create tasks and events, assigning them details like their description or what day they take place on, and then quickly access given dates in a calendar view.",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "This app was developed in Kotlin by Marcus Ferreira and William Ruckh as a final project for SER 210: Software Design & Development.",
                textAlign = TextAlign.Center
            )
        }
    }
}