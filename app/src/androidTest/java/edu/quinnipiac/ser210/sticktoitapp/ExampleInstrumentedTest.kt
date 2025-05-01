package edu.quinnipiac.ser210.sticktoitapp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import edu.quinnipiac.ser210.sticktoitapp.screens.SectionTitle
import org.junit.Rule
import org.junit.Test

class SectionTitleTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun sectionTitle_displaysCorrectTitle() {
        // Arrange
        val testTitle = "Test Section Title"

        // Act
        composeTestRule.setContent {
            SectionTitle(title = testTitle)
        }

        // Assert
        composeTestRule.onNodeWithText(testTitle).assertExists()
    }
}