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
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yourapp.ui.HomeScreen


// Function to add a top bar to the app
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StickToItAppBar(
    currentScreen: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier
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
                        contentDescription = ""
                    )
                }
            }
        }
    )
}

// Function to give navigation to the top bar (not very useful with one current screen)
@RequiresApi(Build.VERSION_CODES.O)
@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun StickNavigation() {
    val navController = rememberNavController()

    val canNavigateBack = navController.currentBackStackEntry != null
    Log.d("canNavigateBack", canNavigateBack.toString())
    Scaffold(
        topBar = {
            StickToItAppBar(
                currentScreen = "StickToIt",
                canNavigateBack = canNavigateBack,
                navigateUp = { navController.navigateUp() },
                modifier = Modifier
            )
        }
    ){ innerPadding ->
        NavHost(navController = navController,
            startDestination = StickScreens.HomeScreen.name, modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)) {

            composable(StickScreens.HomeScreen.name) {
                HomeScreen(navController = navController)
            }
        }
    }
}