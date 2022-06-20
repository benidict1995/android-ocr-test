package com.benidict.domain.helper

import com.benidict.domain.constant.*

fun String.parseFormula(): Pair<Char, Pair<Int, String>> {
    var digit = EMPTY
    var symbol = EXPRESSION_ADD
    var result = 0
    var hasSecondExpression = 0

    for (x in this){
        if (basicArithmeticExpression.contains(x).not() && hasSecondExpression < 2){
            digit += x
        } else {
            hasSecondExpression +=1
            if (hasSecondExpression < 2){
                when(symbol){
                    EXPRESSION_ADD -> result += digit.toInt()
                    EXPRESSION_MUL -> result *= digit.toInt()
                    EXPRESSION_MIN -> result -= digit.toInt()
                    EXPRESSION_DIV -> result /= digit.toInt()
                }
                symbol = x
                digit = EMPTY
            }
        }
    }

    return Pair(symbol, Pair(result, digit))
}


