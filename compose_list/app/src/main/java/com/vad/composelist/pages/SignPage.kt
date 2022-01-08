package com.vad.composelist.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vad.signs.domain.Parser
import com.vad.signs.domain.models.Sign

@Composable
fun ListView(signs: List<Sign>) {
    LazyColumn {
        items(signs.size) { index ->
            Column {
                SignCard(sign = signs[index])
                Divider()
            }
        }
    }
}

@Preview
@Composable
fun ListViewPreview() {
    ListView(
        signs = listOf(
            Sign(
                "P.105",
                title = "Bien 105",
                description = "This is a test description",
                "P105"
            ),
            Sign("P.105", title = "Bien 105", description = "This is a test description", "P105"),
            Sign("P.105", title = "Bien 105", description = "This is a test description", "P105"),
            Sign("P.105", title = "Bien 105", description = "This is a test description", "P105")
        )
    )
}

@Composable
fun SignCard(sign: Sign) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(start = 16.dp)
            .fillMaxWidth()
    ) {
        // this method will get asset image and convert to bitmap
        Parser.assetsToBitmap(LocalContext.current, "signs/${sign.imageName}.webp")?.let {
            Image(
                bitmap = it.asImageBitmap(), sign.title, modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .width(100.dp)
                    .height(100.dp)
                    .align(Alignment.CenterVertically)
            )
            Column(
                modifier = Modifier.padding(top = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(sign.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Blue)
                Text(sign.title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                sign.description?.let {
                    Text(it, fontSize = 14.sp, fontWeight = FontWeight.Light, maxLines = 3)
                }
            }
        }
    }
}

@Preview
@Composable
fun SignCardPreview() {
    SignCard(
        sign = Sign(
            "P.105",
            title = "Bien 105",
            description = "This is a test description",
            "P105"
        )
    )
}