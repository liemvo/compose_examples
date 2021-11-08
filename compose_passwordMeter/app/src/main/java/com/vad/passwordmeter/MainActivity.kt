package com.vad.passwordmeter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.vad.passwordmeter.ui.theme.PasswordMeterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordMeterTheme {
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
    val passwordState = remember {
        mutableStateOf("")
    }
    
    val isShowPassword = remember { mutableStateOf(false) }
    
    fun visualTransformation() = if (isShowPassword.value) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }
    
    fun visualIcon() = if (isShowPassword.value) {
        Icons.Filled.VisibilityOff
    } else {
        Icons.Filled.Visibility
    }
    
    val securityState = remember {
        mutableStateOf(StrengthLevel.minLevel)
    }
    val calculator: CalculatorInterface = PasswordCalculator
    
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = passwordState.value, onValueChange = {
                passwordState.value = it
                securityState.value = calculator.calculatePasswordLevel(it)
            },
            label = { Text("Password") },
            visualTransformation = visualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isShowPassword.value = !isShowPassword.value }) {
                    Icon(imageVector = visualIcon(), contentDescription = null)
                }
            })
        
        ConstraintLayout {
            val (text, box1, box2) = createRefs()
            Box(
                Modifier
                    .constrainAs(box1) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
                    .fillMaxWidth(fraction = 0.65f)
                    .height(6.dp)
                    .background(Color.LightGray)
            )
            
            Box(
                Modifier
                    .constrainAs(box2) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
                    .height(6.dp)
                    .animateContentSize()
            
            ) {
                Text(
                    "",
                    modifier = Modifier
                        .fillMaxWidth(0.65f * securityState.value.percentage)
                        .background(securityState.value.color)
                )
            }
            
            Text(
                modifier = Modifier.constrainAs(text) {
                    top.linkTo(parent.top)
                    start.linkTo(box1.end)
                    bottom.linkTo(parent.bottom)
                }.padding(start = 8.dp),
                text = stringResource(id = securityState.value.textId),
                color = securityState.value.color
            )
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
    PasswordMeterTheme {
        PasswordMeter()
    }
}