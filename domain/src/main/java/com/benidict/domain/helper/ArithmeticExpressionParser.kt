package com.benidict.domain.helper

import com.benidict.domain.constant.*
import com.benidict.domain.model.ResultData

fun String.parseFormula(): ResultData {
    var digit = EMPTY
    var symbol = EXPRESSION_ADD
    var result = 0f
    var hasSecondExpression = 0

    for (x in this){
        if (basicArithmeticExpression.contains(x).not() && hasSecondExpression < 2){
            digit += x
        } else {
            hasSecondExpression +=1
            if (hasSecondExpression < 2){
                when(symbol){
                    EXPRESSION_ADD -> result += digit.toFloat()
                    EXPRESSION_MUL -> result *= digit.toFloat()
                    EXPRESSION_MIN -> result -= digit.toFloat()
                    EXPRESSION_DIV -> result /= digit.toFloat()
                }
                symbol = x
                digit = EMPTY
            }
        }
    }

    return ResultData(
        expression = symbol,
        result = result,
        digit = digit
    )
}


