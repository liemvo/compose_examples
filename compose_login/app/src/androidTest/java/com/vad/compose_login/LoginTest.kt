package com.vad.compose_login

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.printToLog
import com.vad.compose_login.ui.theme.Compose_loginTheme
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
    fun loginScreenIsLoaded() {
        composeTestRule.onNodeWithText("Username").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login").assertHasClickAction()
        composeTestRule.onNodeWithText("Login").printToLog("ABC")
        composeTestRule.onNodeWithText("Login").assertIsNotEnabled()
        composeTestRule.onNodeWithTag("user").assertIsDisplayed()
        composeTestRule.onNodeWithTag("user").printToLog("ABC")
        composeTestRule.onNodeWithTag("password").assertIsDisplayed()
        composeTestRule.onNodeWithTag("password").printToLog("ABC")
    }
}
