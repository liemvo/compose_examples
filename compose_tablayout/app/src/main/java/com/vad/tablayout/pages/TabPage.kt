package com.vad.tablayout.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.vad.signs.domain.models.SignGroup
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun TabScreen(signGroups: List<SignGroup>) {
    if (signGroups.isNotEmpty()) {
        val pagerState = rememberPagerState(pageCount = signGroups.size)
        Column {
            Tabs(pagerState = pagerState, tabTitles = signGroups.map { it.title })
            TabsContent(pagerState = pagerState, signGroups = signGroups)
        }
    } else {
        Text("No Data")
    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState, tabTitles: List<String>) {
    val scope = rememberCoroutineScope()

    ScrollableTabRow(selectedTabIndex = pagerState.currentPage) {
        tabTitles.forEachIndexed { index, _ ->
            Tab(
                text = {
                    Text(tabTitles[index],
                        color = if (pagerState.currentPage == index) Color.White else Color.LightGray
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabsContent(pagerState: PagerState, signGroups: List<SignGroup>) {
    HorizontalPager(state = pagerState) { page ->
        val group = signGroups[page]
        ListView(signs = group.signs)
    }
}
