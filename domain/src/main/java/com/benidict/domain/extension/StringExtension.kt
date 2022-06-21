package com.benidict.domain.extension

import com.benidict.domain.constant.letter
import com.benidict.domain.constant.special
import java.util.regex.Matcher

fun String.isValidFormula(): Boolean {
    val isDigitOnly = this.all { char -> char.isDigit() }
    val hasSpecial: Matcher = special.matcher(this)
    val hasLetter: Matcher = letter.matcher(this)
    return listOf(hasSpecial.find(), hasLetter.find(), isDigitOnly).contains(true).not()
}