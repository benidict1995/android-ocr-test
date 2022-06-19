package com.benidict.android_ocr_test.ui

import androidx.lifecycle.viewModelScope
import com.benidict.android_ocr_test.base.BaseViewModel
import com.benidict.android_ocr_test.constant.INVALID_FORMULA
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
    val computeFormulaUseCase: ComputeFormulaUseCase
) : BaseViewModel() {

    private val mutableMainState: MutableSharedFlow<MainState> = MutableSharedFlow(
        replay = 1
    )

    val maiState = mutableMainState

    fun checkFormula(formula: String) {
        viewModelScope.launch(
            CoroutineExceptionHandler{ _, error ->
                emitState {
                    mutableMainState.emit(
                        MainState.onFormulaError(
                            error.message?:INVALID_FORMULA
                        )
                    )
                }
            }
        ){
            val invoke = checkFormulaUseCase.checkFormula(formula = formula)

        }
    }

}