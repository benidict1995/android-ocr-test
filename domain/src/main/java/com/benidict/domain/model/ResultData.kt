package com.benidict.domain.model

import com.benidict.domain.constant.EMPTY

data class ResultData(
    var expression: Char,
    var formula: String = EMPTY,
    var result: Float = 0f,
    val digit: String = EMPTY
)