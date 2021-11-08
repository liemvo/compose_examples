package com.vad.passwordmeter

import com.nulabinc.zxcvbn.Zxcvbn

interface CalculatorInterface {
    fun calculatePasswordLevel(password: String): StrengthLevel
}

object PasswordCalculator: CalculatorInterface {
    private val calculator = Zxcvbn()
    override fun calculatePasswordLevel(password: String): StrengthLevel {
        // Need cal calculate a password score
        
        return StrengthLevel.ofLevel(calculator.measure(password).score)
    }
}
