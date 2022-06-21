package com.benidict.android_ocr_test.ui

import com.benidict.android_ocr_test.rule.TestCoroutineRule
import com.benidict.domain.model.ResultData
import com.benidict.domain.usecase.CheckFormulaUseCase
import com.benidict.domain.usecase.ComputeFormulaUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@Suppress("DEPRECATION")
@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var computeFormulaUseCase: ComputeFormulaUseCase

    @Mock
    private lateinit var checkFormulaUseCase: CheckFormulaUseCase

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(
            computeFormulaUseCase = computeFormulaUseCase,
            checkFormulaUseCase = checkFormulaUseCase
        )
    }

    @Test
    fun `return correct computation if the formula is valid`() {
        val formula = "1+1"
        val answer = ResultData(
            expression = '+',
            formula = formula,
            result = 2.0f,
            digit = "1"
        )
        runBlocking {
            `when`(checkFormulaUseCase.checkFormula(formula)).thenReturn(formula)
            `when`(computeFormulaUseCase.computeFormula(formula)).thenReturn(answer)
            viewModel.checkFormula(formula)
            Assert.assertEquals(answer, MainState.OnComputationResult(answer).data)
        }
    }

    @Test
    fun `throw arithmetic exception if the formula is invalid`() {
        val formula = "a1+1"
        val error = "Invalid Formula"
        runBlocking {
            `when`(checkFormulaUseCase.checkFormula(formula)).thenReturn(formula)
            viewModel.checkFormula(formula)
            Assert.assertEquals(error, MainState.OnFormulaError(error).err)
        }
    }

}