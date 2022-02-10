package com.vad.composelist.pages

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val DEFAULT_SPACE = 0.dp

data class Spacing(
    val extraSmall: Dp = DEFAULT_SPACE,
    val small: Dp = DEFAULT_SPACE,
    val medium: Dp = DEFAULT_SPACE,
    val large: Dp = DEFAULT_SPACE
)

val LocalSpacing = compositionLocalOf { Spacing() }

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current