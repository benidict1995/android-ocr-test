package com.benidict.android_ocr_test.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.runBlocking

open class BaseViewModel : ViewModel() {
    fun emitState(
        emitAction: suspend () -> Unit
    ) = runBlocking {
        emitAction()
    }
}
