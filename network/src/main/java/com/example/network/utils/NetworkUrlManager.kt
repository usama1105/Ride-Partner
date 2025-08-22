package com.example.network.utils

import com.example.network.utils.NetworkBuilder.API_VERSION
import com.example.network.utils.NetworkBuilder.isDebug


class NetworkUrlManager {
    companion object {
        //actual test server
        private val TEST_BASE_URL =
//        testing
            "https://commutebuddymw-production.up.railway.app/$API_VERSION"

        private val RELEASE_BASE_URL =
//        testing
            "https://commutebuddymw-production.up.railway.app/$API_VERSION"

    }

    fun getUrl(isTestServer: Boolean): NetworkUrl {
        if (isDebug()) {
            return if (isTestServer) NetworkUrl(
                baseUrl = TEST_BASE_URL,
            ) else NetworkUrl(
                baseUrl = RELEASE_BASE_URL,

                )
        }
        return NetworkUrl(
            baseUrl = RELEASE_BASE_URL,
        )
    }

}
data class NetworkUrl(
    val baseUrl: String
)