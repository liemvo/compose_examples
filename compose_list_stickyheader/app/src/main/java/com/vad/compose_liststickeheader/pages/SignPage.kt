package com.vad.compose_liststickeheader.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vad.signs.domain.Parser
import com.vad.signs.domain.models.Sign
import com.vad.signs.domain.models.SignGroup

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


@Composable
fun HeaderView(text: String) {
    Text(
        text,
        fontSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.95f)
            .background(Color.LightGray)
            .padding(16.dp)
    )
}

@Preview
@Composable
fun HeaderPreview() {
    HeaderView("Dangerous")
}

@ExperimentalFoundationApi
@Composable
fun StickyList(groups: List<SignGroup>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        groups.forEach {
            stickyHeader {
                HeaderView(text = it.title)
            } // it is define the header of list

            items(it.signs.size) { index -> // it is the same with the previous video
                Column {
                    SignCard(sign = it.signs[index])
                    Divider()
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun StickyListPreview() {
    val groups = listOf(
        SignGroup(
            "Dangerous",
            listOf( Sign("P.105", title = "Bien 105", description = "This is a test description", "P105"),
                Sign("P.105", title = "Bien 105", description = "This is a test description", "P105"),
                Sign("P.105", title = "Bien 105", description = "This is a test description", "P105"))
        ),
        SignGroup(
            "Guideline",
            listOf( Sign("P.105", title = "Bien 105", description = "This is a test description", "P105"),
                Sign("P.105", title = "Bien 105", description = "This is a test description", "P105"),
                Sign("P.105", title = "Bien 105", description = "This is a test description", "P105"))
        )
    )
    StickyList(groups = groups)
}