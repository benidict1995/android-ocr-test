package com.benidict.domain.constant

import java.util.regex.Pattern

const val CAMERA_REQUEST_PERMISSION = 100

const val INVALID_FORMULA = "Invalid Formula"
const val INVALID_RESULT = "Invalid Result"

val letter = Pattern.compile("[a-zA-z]")
val special = Pattern.compile("[!@#$%&()_=|<>?{}\\[\\]~]")
val basicArithmeticExpression = arrayOf('*','-','/','+')

const val EXPRESSION_ADD = '+'
const val EXPRESSION_MUL = '*'
const val EXPRESSION_DIV = '/'
const val EXPRESSION_MIN = '-'

const val EMPTY = ""