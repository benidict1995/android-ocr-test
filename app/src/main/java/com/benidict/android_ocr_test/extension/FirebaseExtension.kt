package com.benidict.android_ocr_test.extension

import android.graphics.Bitmap
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage

fun Bitmap.processImageVision(onSuccess: (String) -> Unit, onFailed: (String) -> Unit) {
    val firebaseVisionImage = FirebaseVisionImage.fromBitmap(this)
    val firebaseVision = FirebaseVision.getInstance()

    val recognizer = firebaseVision.onDeviceTextRecognizer

    val tasks = recognizer.processImage(firebaseVisionImage)
    tasks.addOnSuccessListener {
        onSuccess(it.text)
    }

    tasks.addOnFailureListener {
        onFailed(it.message ?: it.localizedMessage)
    }
}