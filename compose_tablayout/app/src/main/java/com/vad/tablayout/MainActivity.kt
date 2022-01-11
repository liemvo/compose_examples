package com.vad.tablayout

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.pager.ExperimentalPagerApi
import com.vad.tablayout.pages.TabScreen
import com.vad.tablayout.ui.theme.Compose_listTheme
import com.vad.signs.domain.Parser
import com.vad.signs.domain.models.SignGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalPagerApi
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

@ExperimentalPagerApi
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Greeting() {
    val scope = rememberCoroutineScope()
    val isLoadedData = remember {
        mutableStateOf(false)
    }
    
    val groups = remember {
        mutableStateOf( emptyList<SignGroup>())
    }
    val context = LocalContext.current
    scope.launch(Dispatchers.IO) { 
        groups.value = Parser.parserSign(context)
        isLoadedData.value = true
    }
    
    if (isLoadedData.value) {
        TabScreen(signGroups = groups.value)
    }
}
