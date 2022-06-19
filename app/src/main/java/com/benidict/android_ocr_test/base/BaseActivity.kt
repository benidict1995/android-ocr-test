package com.benidict.android_ocr_test.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.benidict.android_ocr_test.base.hilt.HiltEntryPointActivity

abstract class BaseActivity<VB: ViewBinding>(
    private val setUpViewBinding: (LayoutInflater) -> VB
): HiltEntryPointActivity(){

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setUpViewBinding(layoutInflater)
        setContentView(binding.root)

        setupView()
        observeState()
    }

    open fun setupView(){}
    open fun observeState(){}

}