package edu.quinnipiac.ser210.sticktoitapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.quinnipiac.ser210.sticktoitapp.screens.HomeScreen
import edu.quinnipiac.ser210.sticktoitapp.R
import edu.quinnipiac.ser210.sticktoitapp.screens.CalendarScreen


// Function to add a top bar to the app
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StickToItAppBar(
    currentScreen: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier,
    onCalendarClick: () -> Unit
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
            IconButton(onClick = onCalendarClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = "Open Calendar"
                )
            }
        }
    )
}

// Function to give navigation to the top bar
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StickNavigation() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            StickToItAppBar(
                currentScreen = "StickToIt",
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                modifier = Modifier,
                onCalendarClick = {
                    navController.navigate(StickScreens.CalendarScreen.name)
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
                HomeScreen(navController = navController)
            }
            composable(StickScreens.CalendarScreen.name) {
                CalendarScreen()
            }
        }
    }
}
