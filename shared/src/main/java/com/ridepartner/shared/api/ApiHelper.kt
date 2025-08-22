package com.ridepartner.shared.api

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

object ApiHelper {
    @SuppressLint("HardwareIds")
    fun getDeviceId(activity: Context): String {
        return Settings.Secure.getString(
            activity.contentResolver,
            Settings.Secure.ANDROID_ID
        ).apply {
//            NetworkUtils.deviceId = this
        }
    }
}