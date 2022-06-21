package com.benidict.android_ocr_test.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.benidict.android_ocr_test.BuildConfig
import com.benidict.android_ocr_test.R
import com.benidict.android_ocr_test.base.BaseActivity
import com.benidict.android_ocr_test.databinding.ActivityMainBinding
import com.benidict.android_ocr_test.extension.processImageVision
import com.benidict.android_ocr_test.extension.requestPermission
import com.benidict.android_ocr_test.extension.showToast
import com.benidict.domain.constant.CAMERA_REQUEST_PERMISSION
import com.benidict.domain.constant.FILE_SYSTEM
import com.benidict.domain.constant.GALLERY_REQUEST_PERMISSION
import com.benidict.domain.constant.INVALID_RESULT


@Suppress("DEPRECATION")
class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {

    private val viewModel: MainViewModel by viewModels()

    override fun setupView() {
        super.setupView()
        binding.toolbar.title = BuildConfig.APP_NAME
        binding.btnCamera.text = BuildConfig.BUTTON_NAME
        binding.btnCamera.setOnClickListener {
            requestPermission(Manifest.permission.CAMERA, arrayOf(Manifest.permission.CAMERA)) {
                val requestCode = if (BuildConfig.APP_NAME.contains(FILE_SYSTEM)) {
                    GALLERY_REQUEST_PERMISSION
                } else {
                    it
                }
                openAction(
                    requestCode
                )
            }
        }
    }

    override fun observeState() {
        super.observeState()

        lifecycleScope.launchWhenCreated {
            viewModel.mainState.collect { state ->
                when (state) {
                    is MainState.OnFormulaError -> {
                        binding.tvFormula.text = state.err
                        binding.tvResult.text = INVALID_RESULT
                        showToast(state.err)
                    }
                    is MainState.OnComputationResult -> {
                        binding.tvFormula.text = state.data.formula
                        binding.tvResult.text = state.data.result.toString()
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
                    openAction(requestCode)
                } else {
                    showToast(getString(R.string.permission_denied))
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CAMERA_REQUEST_PERMISSION -> {
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
            GALLERY_REQUEST_PERMISSION -> {
                val imageUri: Uri? = data?.data
                val bitmap =
                    MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                binding.ivCapture.setImageURI(imageUri)
                bitmap.processImageVision(
                    onSuccess = {
                        viewModel.checkFormula(it)
                    }, onFailed = {
                        showToast(it)
                    })
            }
        }
    }

    private fun openAction(requestCode: Int) {
        val i: Intent
        if (BuildConfig.APP_NAME.contains(FILE_SYSTEM)) {
            i = Intent(Intent.ACTION_PICK)
            i.type = "image/*"
        } else {
            i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        }
        startActivityForResult(i, requestCode)
    }
}