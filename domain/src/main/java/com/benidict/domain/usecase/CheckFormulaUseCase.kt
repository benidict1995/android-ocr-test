package com.benidict.domain.usecase

import com.benidict.domain.extension.isValidFormula
import com.benidict.domain.helper.ioDispatcher
import kotlinx.coroutines.withContext

class CheckFormulaUseCase {

    suspend fun checkFormula(formula: String): String =
        withContext(ioDispatcher) {
            if (formula.isEmpty()) {
                throw ArithmeticException()
            } else {
                if (formula.isValidFormula()) {
                    formula
                } else {
                    throw ArithmeticException()
                }
            }
        }

}