package com.vad.compose_liststickeheader

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.vad.compose_liststickeheader.pages.StickyList
import com.vad.compose_liststickeheader.ui.theme.Compose_liststickeheaderTheme
import com.vad.signs.domain.Parser
import com.vad.signs.domain.models.SignGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_liststickeheaderTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting()
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Greeting() {
    val scope = rememberCoroutineScope()
    val isDataLoaded = remember {
        mutableStateOf(false)
    }
    val groups = remember {
        mutableStateOf(listOf<SignGroup>())
    }
    val context = LocalContext.current
    scope.launch(Dispatchers.IO) {
        groups.value = Parser.parserSign(context)
        isDataLoaded.value = true
    }

    if (isDataLoaded.value) {
        StickyList(groups = groups.value)
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Compose_liststickeheaderTheme {
        Greeting()
    }
}