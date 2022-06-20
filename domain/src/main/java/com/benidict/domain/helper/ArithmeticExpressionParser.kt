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
                    EXPRESSION_ADD -> result += Integer.parseInt(digit)
                    EXPRESSION_MUL -> result *= Integer.parseInt(digit)
                    EXPRESSION_DIV -> result -= Integer.parseInt(digit)
                    EXPRESSION_MIN -> result /= Integer.parseInt(digit)
                }
                symbol = x
                digit = EMPTY
            }
        }
    }

    return Pair(symbol, Pair(result, digit))
}


