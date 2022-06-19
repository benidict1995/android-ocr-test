package com.benidict.android_ocr_test.ui

sealed class MainState {
    data class onFormulaError(val err: String): MainState()
}