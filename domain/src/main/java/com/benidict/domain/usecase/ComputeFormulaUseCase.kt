package com.benidict.domain.usecase

import com.benidict.domain.constant.EXPRESSION_ADD
import com.benidict.domain.constant.EXPRESSION_DIV
import com.benidict.domain.constant.EXPRESSION_MIN
import com.benidict.domain.constant.EXPRESSION_MUL
import com.benidict.domain.helper.ioDispatcher
import com.benidict.domain.helper.parseFormula
import kotlinx.coroutines.withContext

class ComputeFormulaUseCase {

    suspend fun computeFormula(formula: String): Int = withContext(ioDispatcher) {
        val result = formula.parseFormula()
        var formulaResult = result.second.first
        when (result.first) {
            EXPRESSION_ADD -> formulaResult += result.second.second.toInt()
            EXPRESSION_MUL -> formulaResult *= result.second.second.toInt()
            EXPRESSION_MIN -> formulaResult -= result.second.second.toInt()
            EXPRESSION_DIV -> formulaResult /= result.second.second.toInt()
        }
        formulaResult
    }
}