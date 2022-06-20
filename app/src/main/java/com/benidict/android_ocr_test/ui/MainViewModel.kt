package com.benidict.android_ocr_test.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.benidict.android_ocr_test.base.BaseViewModel
import com.benidict.domain.constant.INVALID_FORMULA
import com.benidict.domain.usecase.CheckFormulaUseCase
import com.benidict.domain.usecase.ComputeFormulaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkFormulaUseCase: CheckFormulaUseCase,
    private val computeFormulaUseCase: ComputeFormulaUseCase
) : BaseViewModel() {

    private val mutableMainState: MutableSharedFlow<MainState> = MutableSharedFlow(
        replay = 1
    )

    val maiState = mutableMainState

    fun checkFormula(formula: String) {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, error ->
                emitState {
                    mutableMainState.emit(
                        MainState.OnFormulaError(
                            error.message ?: INVALID_FORMULA
                        )
                    )
                }
            }
        ) {
            val invoke = checkFormulaUseCase.checkFormula(formula = formula)
            val computation = computeFormulaUseCase.computeFormula(invoke)
            mutableMainState.emit(
                MainState.OnComputationResult(result = computation, formula = invoke)
            )
        }
    }

}