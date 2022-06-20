package com.benidict.android_ocr_test.ui

sealed class MainState {
    data class OnFormulaError(val err: String): MainState()
    data class OnComputationResult(val result: Int, val formula: String): MainState()
}