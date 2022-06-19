package com.benidict.domain.module

import com.benidict.domain.usecase.CheckFormulaUseCase
import com.benidict.domain.usecase.ComputeFormulaUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun checkFormulaUseCase() = CheckFormulaUseCase()

    @Provides
    @ViewModelScoped
    fun computeFormulaUseCase() = ComputeFormulaUseCase()
}