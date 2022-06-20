package com.benidict.domain.usecase

import com.benidict.domain.constant.EXPRESSION_ADD
import com.benidict.domain.constant.EXPRESSION_DIV
import com.benidict.domain.constant.EXPRESSION_MIN
import com.benidict.domain.constant.EXPRESSION_MUL
import com.benidict.domain.helper.ioDispatcher
import com.benidict.domain.helper.parseFormula
import com.benidict.domain.model.ResultData
import kotlinx.coroutines.withContext

class ComputeFormulaUseCase {

    suspend fun computeFormula(formula: String): ResultData = withContext(ioDispatcher) {
        val result = formula.parseFormula()
        var formulaResult = result.result
        when (result.expression) {
            EXPRESSION_ADD -> formulaResult += result.digit.toFloat()
            EXPRESSION_MUL -> formulaResult *= result.digit.toFloat()
            EXPRESSION_MIN -> formulaResult -= result.digit.toFloat()
            EXPRESSION_DIV -> formulaResult /= result.digit.toFloat()
        }
        result.copy(
            formula = formula,
            result = formulaResult
        )
    }
}