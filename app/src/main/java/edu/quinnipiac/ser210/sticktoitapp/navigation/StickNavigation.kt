package edu.quinnipiac.ser210.sticktoitapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.quinnipiac.ser210.sticktoitapp.R
import edu.quinnipiac.ser210.sticktoitapp.screens.CalendarScreen
import edu.quinnipiac.ser210.sticktoitapp.screens.HomeScreen
import edu.quinnipiac.ser210.sticktoitapp.viewmodel.DateViewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.rememberUpdatedState
import androidx.navigation.compose.currentBackStackEntryAsState
import edu.quinnipiac.ser210.sticktoitapp.screens.CreateEntryScreen
import edu.quinnipiac.ser210.sticktoitapp.screens.SettingsScreen
import edu.quinnipiac.ser210.sticktoitapp.viewmodel.TaskEventViewModel

// Function to add a top bar to the app
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StickToItAppBar(
    currentScreen: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier,
    onCalendarClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onCreateEntryClick: () -> Unit
) {
    TopAppBar(
        title = { Text("StickToIt") },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onCreateEntryClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Create Entry"
                )
            }
            IconButton(onClick = onCalendarClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = "Open Calendar"
                )
            }
            IconButton(onClick = onSettingsClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = "Open Settings"
                )
            }
        }

    )
}

// Function to give navigation to the top bar
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StickNavigation(navController: NavHostController, viewModel: DateViewModel, taskEventViewModel: TaskEventViewModel) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentScreen = rememberUpdatedState(currentDestination?.route ?: "")

    Scaffold(
        topBar = {
            StickToItAppBar(
                currentScreen = currentScreen.value,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                modifier = Modifier,
                onCalendarClick = {
                    navController.navigate(StickScreens.CalendarScreen.name)
                },
                onSettingsClick = {
                    navController.navigate(StickScreens.SettingsScreen.name)
                },
                onCreateEntryClick = {
                    navController.navigate(StickScreens.CreateEntryScreen.name)
                }
            )

        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = StickScreens.HomeScreen.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(StickScreens.HomeScreen.name) {
                HomeScreen(navController = navController, viewModel = viewModel, taskEventViewModel = taskEventViewModel)
            }
            composable(StickScreens.CalendarScreen.name) {
                CalendarScreen(navController = navController, viewModel = viewModel)
            }
            composable(StickScreens.SettingsScreen.name) {
                SettingsScreen(navController = navController, taskEventViewModel = taskEventViewModel)
            }
            composable(StickScreens.CreateEntryScreen.name) {
                CreateEntryScreen(navController = navController, taskEventViewModel = taskEventViewModel)
            }
        }
    }
}