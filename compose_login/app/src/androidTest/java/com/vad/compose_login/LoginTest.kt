package com.vad.compose_login

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import com.vad.compose_login.ui.theme.Compose_loginTheme
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

fun SemanticsNodeInteraction.imeActionValue(): String? {
    for ((key, value) in fetchSemanticsNode().config) {
        if (key.name == "ImeAction"){
            return value?.toString()
        }
    }
    return null
}

fun SemanticsNodeInteraction.currentText(): String? {
    for ((key, value) in fetchSemanticsNode().config) {
        if (key.name == "EditableText"){
            return value?.toString()
        }
    }
    return null
}

class LoginTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            Compose_loginTheme {
                LoginView()
            }
        }
    }

    @Test
    fun loginIsLoaded() {
        val button = composeTestRule.onNodeWithText("Login")
        button.assertIsDisplayed()
        button.assertIsNotEnabled()

        val username = composeTestRule.onNodeWithTag("user")
        username.assertIsDisplayed()
        assertEquals("Next", username.imeActionValue())
        assertTrue(username.currentText().isNullOrEmpty()!!)
        val password = composeTestRule.onNodeWithTag("password")
        password.assertIsDisplayed()
        assertEquals("Done", password.imeActionValue())
        assertTrue(password.currentText().isNullOrEmpty()!!)
    }

    @Test
    fun loginEnabledIsLoaded() {
        val button = composeTestRule.onNodeWithText("Login")
        button.assertIsDisplayed()
        button.assertIsNotEnabled()

        val username = composeTestRule.onNodeWithTag("user")
        username.performTextInput("user")
        username.assertIsDisplayed()
        assertEquals("Next", username.imeActionValue())
        assertEquals("user", username.currentText())

        val password = composeTestRule.onNodeWithTag("password")
        password.performTextInput("hellothere")
        password.assertIsDisplayed()
        assertEquals("Done", password.imeActionValue())
        assertEquals("hellothere", password.currentText())

        button.assertIsEnabled()
    }
}