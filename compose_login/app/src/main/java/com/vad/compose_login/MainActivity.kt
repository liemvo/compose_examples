package com.vad.compose_login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vad.compose_login.ui.theme.Compose_loginTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_loginTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LoginView()
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginView(modifier: Modifier = Modifier) {
    val focusRequester = remember {
        FocusRequester()
    }
    val usernameSate = remember { mutableStateOf("") }
    val passwordSate = remember { mutableStateOf("") }
    val isVisibility = remember {
        mutableStateOf(false)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = usernameSate.value,
            onValueChange = {
                usernameSate.value = it
            },
            label = { Text("Username") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),

            keyboardActions = KeyboardActions(
                onNext = {
                    focusRequester.requestFocus()
                }
            )
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
            value = passwordSate.value,
            onValueChange = { passwordSate.value = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = if (isVisibility.value) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(onClick = { isVisibility.value = !isVisibility.value }) {
                    Icon(
                        imageVector = if (isVisibility.value) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        }, contentDescription = null
                    )
                }
            },

            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )
        Button(
            modifier = Modifier.padding(top = 16.dp),
            enabled = usernameSate.value.isNotEmpty() && passwordSate.value.isNotEmpty(),
            onClick = { /*TODO*/ }) {
            Text("Login")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Compose_loginTheme {
        LoginView()
    }
}