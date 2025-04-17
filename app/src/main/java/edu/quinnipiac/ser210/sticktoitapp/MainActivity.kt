package edu.quinnipiac.ser210.sticktoitapp
// William Ruckh & Marcus Ferreira
// StickToIt: A Calendar & Reminder app to help you manage your daily events & tasks
// Final Project for SER 210
// Built from 4/14/2025 to 4/30/2025
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import edu.quinnipiac.ser210.sticktoitapp.navigation.StickNavigation
import edu.quinnipiac.ser210.sticktoitapp.ui.theme.StickToItAppTheme


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StickToItAppTheme {
                StickNavigation()
            }
        }
    }
}

// Preview function
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun StickToItPreview() {
    StickToItAppTheme {
        StickNavigation()
    }
}