package com.vad.passwordmeter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.vad.passwordmeter.ui.theme.Password_meterTheme
import java.util.Locale
import java.util.regex.Pattern

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Password_meterTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    PasswordMeter()
                }
            }
        }
    }
}

@Composable
fun PasswordMeter() {
    val passwordSate = remember {
        mutableStateOf("")
    }

    val isShowPassword = remember {
        mutableStateOf(false)
    }

    val passwordLevel = remember {
        mutableStateOf(SecurityLevel.minLevel)
    }

    val passwordCalculator = object : PasswordCalculator {
        override fun calculateSecurityLevel(password: String?): SecurityLevel {
            if (password == null) return SecurityLevel.minLevel
            if (password.isEmpty() || password.length < minimumLength) return SecurityLevel.minLevel

            var level = 1

            if (password.length >= (minimumLength * 1.5)) ++level

            val locale = Locale.getDefault()
            val hasUppercase = password != password.lowercase(locale)
            val hasLowercase = password != password.uppercase(locale)
            if (hasLowercase && hasUppercase) ++level

            val hasNumber = Pattern.matches(".*\\d.*", password)
            if(hasNumber) ++level

            val hasSpecialCharacter = Pattern.matches(".*[!@#\$@()].*", password)
            if (hasSpecialCharacter) ++level

            return SecurityLevel.ofLevel(level)
        }
    }

    fun visualTransformation() = if(isShowPassword.value) VisualTransformation.None else PasswordVisualTransformation()
    fun iconVisual() = if(isShowPassword.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = passwordSate.value, onValueChange = {
            passwordSate.value = it
            passwordLevel.value = passwordCalculator.calculateSecurityLevel(it)
        },
        label = { Text("Password")},
        visualTransformation = visualTransformation(),
        trailingIcon = {
            IconButton(onClick = { isShowPassword.value = !isShowPassword.value }) {
                Icon(imageVector = iconVisual(), contentDescription = null)
            }
        })

        ConstraintLayout(Modifier.fillMaxWidth()) {
            val context = LocalContext.current
            val (box, text) = createRefs()

            Box(
                Modifier
                    .constrainAs(box) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)

                    }
                    .fillMaxWidth(fraction = 0.65f * passwordLevel.value.percentage())
                    .height(8.dp)
                    .background(passwordLevel.value.color)
            )
            Text(context.getString(passwordLevel.value.textId), modifier = Modifier
                .constrainAs(text) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
                .width(90.dp))

        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Password_meterTheme {
        PasswordMeter()
    }
}