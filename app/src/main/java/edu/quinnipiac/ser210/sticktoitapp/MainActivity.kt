package edu.quinnipiac.ser210.sticktoitapp
// William Ruckh & Marcus Ferreira
// StickToIt: A Calendar & Reminder app to help you manage your daily events & tasks
// Final Project for SER 210
// Built from 4/14/2025 to 5/1/2025
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import edu.quinnipiac.ser210.sticktoitapp.data.AppDatabase
import edu.quinnipiac.ser210.sticktoitapp.data.AppRepository
import edu.quinnipiac.ser210.sticktoitapp.navigation.StickNavigation
import edu.quinnipiac.ser210.sticktoitapp.screens.CreateEntryScreen
import edu.quinnipiac.ser210.sticktoitapp.ui.theme.StickToItAppTheme
import edu.quinnipiac.ser210.sticktoitapp.viewmodel.DateViewModel
import edu.quinnipiac.ser210.sticktoitapp.viewmodel.TaskEventViewModel


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StickToItAppTheme {
                Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
                ) {
                    // Values for the room database
                    val appDatabase = AppDatabase.getDatabase(this)
                    val taskDao = appDatabase.taskDao()
                    val eventDao = appDatabase.eventDao()
                    val appRepository = AppRepository(taskDao, eventDao)

                    // ViewModels and navController
                    val dateViewModel = viewModel<DateViewModel>()
                    val taskEventViewModel = TaskEventViewModel(appRepository)

                    val navController = rememberNavController()

                    StickNavigation(navController, dateViewModel, taskEventViewModel)
                }
            }
        }
    }
}

//// Preview function
//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
//@Composable
//fun StickToItPreview() {
//    StickToItAppTheme {
//        CreateEntryScreen(navController = rememberNavController())
//    }
//}