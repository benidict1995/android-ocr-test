package com.benidict.android_ocr_test.extension

import com.benidict.domain.extension.isValidFormula
import org.junit.Assert
import org.junit.Test

class StringExtensionTest {

    @Test
    fun `check if the formula is valid, it will return true`() {
        val formula = "11+14"
        val expected = true
        Assert.assertEquals(expected, formula.isValidFormula())
    }

    @Test
    fun `check if the formula is digit only, it will return as invalid or false`() {
        val formula = "1111"
        val expected = false
        Assert.assertEquals(expected, formula.isValidFormula())
    }

    @Test
    fun `check if the formula is compose of special character, it will return as invalid or false`() {
        val formula = "11+>=11"
        val expected = false
        Assert.assertEquals(expected, formula.isValidFormula())
    }

    @Test
    fun `check if the formula is compose of alpha, it will return as invalid formula or false`() {
        val formula = "111a1"
        val expected = false
        Assert.assertEquals(expected, formula.isValidFormula())
    }
}