package com.vad.passwordmeter

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

enum class SecurityLevel(@StringRes val textId: Int, val color: Color) {
    TOO_WEAK(R.string.too_weak, Color.Black),
    WEAK(R.string.weak, Color.Red),
    MEDIUM(R.string.medium, Color.Yellow),
    STRONG(R.string.strong, Color.Blue),
    TOO_STRONG(R.string.too_strong, Color.Green);

    fun percentage(): Float = (ordinal + 1).toFloat() / values().size.toFloat()

    companion object {
        val minLevel get() = values().first()
        fun ofLevel(level: Int): SecurityLevel = if (level < values().size) {
            values()[level]
        } else {
            minLevel
        }
    }
}