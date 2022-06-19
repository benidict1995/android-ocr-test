package com.benidict.android_ocr_test.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.benidict.android_ocr_test.R
import com.benidict.android_ocr_test.base.BaseActivity
import com.benidict.android_ocr_test.constant.CAMERA_REQUEST_PERMISSION
import com.benidict.android_ocr_test.constant.INVALID_RESULT
import com.benidict.android_ocr_test.databinding.ActivityMainBinding
import com.benidict.android_ocr_test.extension.processImageVision
import com.benidict.android_ocr_test.extension.requestPermission
import com.benidict.android_ocr_test.extension.showToast


@Suppress("DEPRECATION")
class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {

    private val viewModel: MainViewModel by viewModels()

    override fun setupView() {
        super.setupView()

        binding.btnCamera.setOnClickListener {
            requestPermission(Manifest.permission.CAMERA, arrayOf(Manifest.permission.CAMERA)) {
                openCamera(it)
            }
        }
    }

    override fun observeState() {
        super.observeState()

        lifecycleScope.launchWhenCreated {
            viewModel.maiState.collect { state ->
                when (state) {
                    is MainState.onFormulaError -> {
                        binding.tvFormula.text = state.err
                        binding.tvResult.text = INVALID_RESULT
                        showToast(state.err)
                    }
                }
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST_PERMISSION -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    openCamera(requestCode)
                } else {
                    showToast(getString(R.string.permission_denied))
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val bundle: Bundle = data?.extras ?: Bundle()

        bundle.let {
            val bitmap: Bitmap = bundle.get("data") as Bitmap
            binding.ivCapture.setImageBitmap(bitmap)

            bitmap.processImageVision(
                onSuccess = {
                    viewModel.checkFormula(it)
                }, onFailed = {
                    showToast(it)
                })
        }
    }

    private fun openCamera(requestCode: Int) {
        val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(i, requestCode)
    }

}