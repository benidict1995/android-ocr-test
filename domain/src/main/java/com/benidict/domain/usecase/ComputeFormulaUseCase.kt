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
            EXPRESSION_ADD -> formulaResult += Integer.parseInt(result.second.second)
            EXPRESSION_MUL -> formulaResult *= Integer.parseInt(result.second.second)
            EXPRESSION_DIV -> formulaResult -= Integer.parseInt(result.second.second)
            EXPRESSION_MIN -> formulaResult /= Integer.parseInt(result.second.second)
        }
        formulaResult
    }
}