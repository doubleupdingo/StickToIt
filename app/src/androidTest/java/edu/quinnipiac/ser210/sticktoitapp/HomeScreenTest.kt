package edu.quinnipiac.ser210.sticktoitapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    @Test
    fun homeScreen_isDisplayed() {
        composeTestRule.onNodeWithText("Today's Events:").assertIsDisplayed()
    }

    @Test
    fun homeScreen_navigateToSettingsScreen() {
        composeTestRule.waitUntil(timeoutMillis = 100000) {
            composeTestRule
                .onAllNodesWithContentDescription("Open Settings")
                .fetchSemanticsNodes().isNotEmpty()
        }
        // Act: Click the settings icon
        composeTestRule.onNodeWithContentDescription("Open Settings").performClick()

        // Assert: We navigated to the settings screen
        composeTestRule
            .onNodeWithText("About StickToIt:")
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_navigateToCalendarScreen() {
        composeTestRule.waitUntil(timeoutMillis = 100000) {
            composeTestRule
                .onAllNodesWithContentDescription("Open Calendar")
                .fetchSemanticsNodes().isNotEmpty()
        }
        // Act: Click the settings icon
        composeTestRule.onNodeWithContentDescription("Open Calendar").performClick()

        // Assert: We navigated to the settings screen
        composeTestRule
            .onNodeWithText("Mon")
            .assertIsDisplayed()
    }
}