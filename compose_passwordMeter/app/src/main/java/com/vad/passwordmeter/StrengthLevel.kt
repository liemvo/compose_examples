package com.vad.passwordmeter

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

enum class StrengthLevel(@StringRes val textId: Int, val color: Color) {
    TOO_WEAK(R.string.too_weak, Color.Black),
    WEAK(R.string.weak, Color.Red),
    FAIR(R.string.fair, Color.Yellow),
    GOOD(R.string.good, Color.Blue),
    STRONG(R.string.strong, Color.Green);
    
    val percentage: Float get() = ordinal.toFloat() / (items.size - 1).toFloat()
    
    val items get() = values()
    
    companion object {
        val minLevel = TOO_WEAK
        fun ofLevel(score: Int) = if (score < 0) {
            minLevel
        } else if (score < values().size) {
            values()[score]
        } else {
            STRONG
        }
    }
}