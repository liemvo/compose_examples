package com.vad.composelist

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.vad.composelist.pages.ListView
import com.vad.composelist.ui.theme.Compose_listTheme
import com.vad.signs.domain.Parser
import com.vad.signs.domain.models.Sign
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_listTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting()
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Greeting() {
    val scope = rememberCoroutineScope()
    val isLoadedData = remember {
        mutableStateOf(false)
    }
    
    val signs = remember {
        mutableStateOf( emptyList<Sign>())
    }
    val context = LocalContext.current
    scope.launch(Dispatchers.IO) { 
        signs.value = Parser.parserSign(context).first().signs
        isLoadedData.value = true
    }
    
    if (isLoadedData.value) {
        ListView(signs = signs.value)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Compose_listTheme {
        Greeting()
    }
}