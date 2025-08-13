package com.example.network.logs

import com.example.network.utils.NetworkBuilder.isDebug

object ApiLogger {
    private val logs = mutableListOf<ApiLog>()

    private var mOnUpdateListener: ((Int) -> Unit)? = null


    fun setUpdateListener(onUpdateListener: (Int) -> Unit) {
        mOnUpdateListener = onUpdateListener
    }

    fun logApiData(apiLog: ApiLog) {
        if (isDebug()) {
            addLogs(apiLog)
        }
    }

    private fun addLogs(apiLog: ApiLog) {
        logs.add(apiLog)
        mOnUpdateListener?.invoke(logs.size)
    }

    fun getApiLogs(): List<ApiLog> {
        return logs
    }
}