package com.vad.passwordmeter

interface PasswordCalculator {
    val minimumLength: Int
        get() = 8

    fun calculateSecurityLevel(password: String?) : SecurityLevel
}