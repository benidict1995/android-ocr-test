package com.benidict.android_ocr_test.ui

import com.benidict.domain.model.ResultData

sealed class MainState {
    data class OnFormulaError(val err: String): MainState()
    data class OnComputationResult(val data: ResultData): MainState()
}