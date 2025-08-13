package com.example.network.utils

object NetworkBuilder {
    lateinit var baseUrl: String
    private var isDebug = true
    var API_VERSION = "api/v1/"
    private var isMocked = false

    fun init(
        isMocked: Boolean,
        isDebug: Boolean,
        apiVersion: String,
        isTestServer: Boolean,
    ) {

        val isTest = isTestServer && isDebug
        this.baseUrl = NetworkUrlManager().getUrl(isTestServer = isTest).baseUrl
        this.isMocked = isMocked
        this.isDebug = isDebug
        this.API_VERSION = apiVersion

    }

    fun isDebug() = isDebug
}