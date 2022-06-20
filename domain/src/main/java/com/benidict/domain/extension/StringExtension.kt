package com.benidict.domain.extension

import androidx.core.text.isDigitsOnly
import com.benidict.domain.constant.letter
import com.benidict.domain.constant.special
import java.util.regex.Matcher

fun String.isValidFormula(): Boolean {
    val isDigitOnly = this.isDigitsOnly()
    val hasSpecial: Matcher = special.matcher(this)
    val hasLetter: Matcher = letter.matcher(this)
    return (hasSpecial.find() || hasLetter.find() || isDigitOnly).not()

}