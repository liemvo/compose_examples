package com.vad.compose_dialogs

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vad.compose_dialogs.ui.theme.Compose_dialogsTheme
import java.util.*

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_dialogsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Buttons()
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun Buttons() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val isShowAlert = remember { mutableStateOf(false) }
        val singleDialog = remember { mutableStateOf(false) }
        val multiDialog = remember {
            mutableStateOf(false)
        }

        val inputDialogState = remember {
            mutableStateOf(false)
        }
        OutlinedButton(onClick = {
            isShowAlert.value = true
        }) {
            Text("Alert")
        }

        if (isShowAlert.value) {
            AlertDialogView(state = isShowAlert)
        }

        OutlinedButton(onClick = {
            singleDialog.value = true
        }) {
            Text("Single Choice")
        }

        if (singleDialog.value) {
            AlertSingleChoiceView(state = singleDialog)
        }

        OutlinedButton(onClick = {
            multiDialog.value = true
        }) {
            Text("Multiple Choices")
        }
        if (multiDialog.value) {
            AlertMultiChoicesView(state = multiDialog)
        }

        OutlinedButton(onClick = {
            inputDialogState.value = true
        }) {
            Text("Input Dialogs")
        }
        if (inputDialogState.value) {
            CommonDialog(title = null, state = inputDialogState) {
                InputView()
            }
        }

        TimePickerDialogView()
    }
}

@Composable
fun TimePickerDialogView() {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val hourState = remember { mutableStateOf(calendar[Calendar.HOUR_OF_DAY]) }
    val minuteState = remember { mutableStateOf(calendar[Calendar.MINUTE]) }
    val timePickerDialog = TimePickerDialog(
        context,
        {_, hour: Int, minute: Int ->
            hourState.value = hour
            minuteState.value = minute
        }, hourState.value, minuteState.value, true
    )

    OutlinedButton(onClick = { timePickerDialog.show() }) {
        Text("Edit time! ${hourState.value}:${minuteState.value}")
    }
}

@ExperimentalComposeUiApi
@Composable
fun InputView() {
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Input dialog", fontSize = 16.sp)
        Divider()
        LoginEmailAndPassword()
    }
}

@ExperimentalComposeUiApi
@Composable
fun LoginEmailAndPassword(
    modifier: Modifier = Modifier,
    username: MutableState<String> = remember {
        mutableStateOf("")
    }, password: MutableState<String> = remember {
        mutableStateOf("")
    }
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val isShowPassword = remember { mutableStateOf(false) }
    fun visualTransformation() = if (isShowPassword.value) VisualTransformation.None else PasswordVisualTransformation()

    fun visualIcon() = if (isShowPassword.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

    Column(
        modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            modifier= Modifier.fillMaxWidth(),
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                keyboardController?.hide()
                focusRequester.requestFocus()
            }),
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            visualTransformation = visualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isShowPassword.value = !isShowPassword.value }) {
                    Icon(imageVector = visualIcon(), contentDescription = null)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            })
        )
    }
}

@Composable
fun AlertMultiChoicesView(state: MutableState<Boolean>) {
    CommonDialog(title = "Multi Choices Dialog", state = state) {
        MultiChoicesView()
    }
}

@Composable
fun AlertSingleChoiceView(state: MutableState<Boolean>) {
    CommonDialog(title = "Single Choice Dialog", state = state) {
        SingleChoiceView()
    }
}

@Composable
fun SingleChoiceView() {
    val radioOptions = listOf("Option 1", "Option 2", "Option 3")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[1]) }
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                        }
                    )
                    .padding(vertical = 8.dp)
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = { onOptionSelected(text) }
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1.merge(),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}


@Composable
fun AlertDialogView(state: MutableState<Boolean>) {
    CommonDialog(title = "Alert Dialog", state = state) {
        Text("JetPack Compose Alert Dialog!")
    }
}

@Composable
fun CommonDialog(
    title: String?,
    state: MutableState<Boolean>,
    content: @Composable (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = {
            state.value = false
        },
        title = title?.let {
            {
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = title)
                    Divider(modifier = Modifier.padding(bottom = 8.dp))
                }
            }
        },
        text = content,
        dismissButton = {
            Button(onClick = { state.value = false }) {
                Text("Cancel")
            }
        },
        confirmButton = {
            Button(onClick = { state.value = false }) {
                Text("Ok")
            }
        }, modifier = Modifier.padding(vertical = 8.dp)
    )
}


@Composable
fun MultiChoicesView() {
    val checkOptions = listOf("Check 1", "Check 2", "Check 3")
    val checkStates = checkOptions.map { remember { mutableStateOf(false) } }
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        checkOptions.forEachIndexed { index, text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = checkStates[index].value,
                        onClick = { checkStates[index].value = !checkStates[index].value }
                    )
                    .padding(vertical = 8.dp)
            ) {
                Checkbox(
                    checked = checkStates[index].value,
                    onCheckedChange = { }
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1.merge(),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Compose_dialogsTheme {
        Buttons()
    }
}

@Preview(showBackground = true)
@Composable
fun SingleChoicePreview() {
    Compose_dialogsTheme {
        SingleChoiceView()
    }
}

@Preview(showBackground = true)
@Composable
fun MultiChoicePreview() {
    Compose_dialogsTheme {
        MultiChoicesView()
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun InputPreview() {
    Compose_dialogsTheme {
        InputView()
    }
}

@Preview(showBackground = true)
@Composable
fun TimePickerDialogPreview() {
    Compose_dialogsTheme {
        TimePickerDialogView()
    }
}