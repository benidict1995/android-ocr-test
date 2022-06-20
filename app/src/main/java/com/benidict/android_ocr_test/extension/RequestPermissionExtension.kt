package com.benidict.android_ocr_test.extension

import android.app.Activity
import android.content.pm.PackageManager
import com.benidict.domain.constant.CAMERA_REQUEST_PERMISSION

fun Activity.requestPermission(checkPermission: String, permission: Array<String>, proceed: (Int) -> Unit) {
    this.run {
        if (checkSelfPermission(checkPermission) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                permission, CAMERA_REQUEST_PERMISSION
            )
        } else {
            proceed(CAMERA_REQUEST_PERMISSION)
        }
    }
}