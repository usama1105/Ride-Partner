package com.example.network.utils

import android.util.Log
import com.example.network.RequestModel
import com.example.network.api.ApiService
import com.example.network.logs.ApiLog
import com.example.network.logs.ApiLogger
import com.example.network.utils.NetworkBuilder.baseUrl
import com.example.network.utils.NetworkBuilder.isDebug
import com.example.network.utils.errors.ApiErrorHandler.getErrorMessage
import com.example.network.utils.errors.ApiErrorHandler.getNetworkErrorMessage
import com.example.network.utils.errors.ApiErrorModel
import com.example.network.utils.errors.ErrorModel
import com.example.network.utils.errors.NetworkErrorHandler
import com.example.network.utils.errors.toErrorModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.MultipartBody
import org.json.JSONObject
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Response

object NetworkUtils {
    var accessToken = ""
    var deviceId = ""
}

val apiService: ApiService by inject(ApiService::class.java)
var isEncryptionEnabled = true
val gson = Gson()


suspend inline fun <T : Any, reified V : Any> performPostRequest(
    apiName: String,
    model: T,
): Resource<V> {
    val response: Pair<Int, Resource<V>> = performPostRequestSimple(apiName, model)
    return response.second
}

suspend inline fun <T : Any, reified V : Any> performMultipleImagesMultiPartRequest(
    apiName: String,
    model: T,
    list1: List<MultipartBody.Part>,
    list2: List<MultipartBody.Part>
): Resource<V> {
    val response: Pair<Int, Resource<V>> = performMultiPartRequestWithMultipleLists(
        apiName = apiName,
        model = model, list1 = list1, list2 = list2
    )
    return response.second
}

suspend inline fun <T : Any, reified V : Any> performPostRequestSimple(
    apiName: String,
    model: T,
): Pair<Int, Resource<V>> {
    val prettyGson = GsonBuilder().serializeNulls().setPrettyPrinting().create()
    val input = prettyGson.toJson(model)
    showErrorLog<ApiLogger>("Request --> $apiName\n${input}")
    val apiFullName = "$baseUrl$apiName"
    var response: Response<JsonObject>? = null

    return try {
        response = apiService.performPostRequest(
            url = apiFullName,
            inputModel = processRequest(input)
        )
        Log.d("isSusccess", response.isSuccessful.toString())
        if (response.isSuccessful) {
            Log.d("isSusccess", true.toString())
            ApiLogger.logApiData(
                apiLog = ApiLog(
                    apiName = apiFullName.getApiEndpoint(),
                    url = apiFullName,
                    request = input.formatJsonString(),
                    response = response.body().toString().toPrettyJson(),
                    responseCode = response.code()
                )
            )
        } else {
            Log.d("isSusccess", false.toString())
            val errorModel = response.getErrorModel(gson).toErrorModel(response.code())
            ApiLogger.logApiData(
                apiLog = ApiLog(
                    apiName = apiFullName.getApiEndpoint(),
                    url = apiFullName,
                    request = input.formatJsonString(),
                    response = errorModel.toJson().toPrettyJson(),
                    responseCode = response.code()
                )
            )
            Log.d("isSusccess", "In Invalid")
            return Pair(
                response.code(),
                Resource.Invalid(errorModel)
            )
        }


        val obj: V = parseResponse(response)
            ?: return Pair(
                response.code(),
                NetworkErrorHandler.getGenericError(response.code())
            ).apply {
                Log.d("isSusccess", "returning InValid")
            }


        //log here retun a vlaid res
        Log.d("isSusccess", "In Valid")
        return Pair(response.code(), Resource.Valid(obj))
    } catch (ex: Exception) {
        ex.printStackTrace()
        //in catch or not
        Log.d("isSusccess", false.toString())
        val apiErrorModel = response.getErrorModel(gson)
        ApiLogger.logApiData(
            apiLog = ApiLog(
                apiName = apiFullName.getApiEndpoint(),
                url = apiFullName,
                request = input.formatJsonString(),
                response = apiErrorModel.toJson().toPrettyJson(),
                responseCode = response?.code() ?: 0
            )
        )
        Log.d("isSusccess", "In Valid")
        Pair(
            response?.code() ?: -1,
            Resource.Invalid(
                apiErrorModel.toErrorModel(
                    responseCode = response?.code(),
                    description = when {
                        ex.message?.contains("unable to resolve host", ignoreCase = true
                        ) == true ||
                                ex.message?.contains("network is unreachable", ignoreCase = true
                                ) == true -> {
                            "Internet -> ${
                                ex.message.orEmpty().cleanExceptionMessage().ifEmpty {
                                    "Check your internet connection"
                                }
                            }"
                        }
                        else -> {
                            "Exception -> ${
                                ex.message.orEmpty().cleanExceptionMessage().ifEmpty {
                                    "Some error occurred"
                                }
                            }"
                        }
                    }
                )
            )
        )
    }
}

fun String?.cleanExceptionMessage(): String {
    if (this.isNullOrEmpty()) return "Some error occurred."

    return this
        // Remove URLs (http/https)
        .replace(Regex("https?://[\\w.-]+(?:\\.[a-zA-Z]{2,})?(?:/[\\w.-]*)*/?"),
            "server")
        // Handle specific connection failure patterns
        .replace(Regex("failed to connect to [\\w.-]+(?:\\.[a-zA-Z]{2,})?(?:/[\\w.-]*)*/?",
            RegexOption.IGNORE_CASE), "failed to connect to server")
        .replace(Regex("unable to resolve host [\\w.-]+(?:\\.[a-zA-Z]{2,})?",
            RegexOption.IGNORE_CASE), "failed to connect to server")
        // Handle timeout messages
        .replace(Regex("timeout.*?[\\w.-]+(?:\\.[a-zA-Z]{2,})?",
            RegexOption.IGNORE_CASE), "connection timeout")
        // Remove any remaining domain-like patterns
        .replace(Regex("[\\w-]+\\.[\\w.-]+\\.[a-zA-Z]{2,}"), "server")
        // Clean up multiple spaces and trim
        .replace(Regex("\\s+"), " ")
        .trim()
        .ifEmpty { "Some error occurred." }
}


inline fun <reified V> parseResponse(response: Response<JsonObject>): V? {
    val jsonObject = if (isEncryptionEnabled) response.body()?.get("res")
        ?.asString?.decrypt().applyLogError("Response After Decryption:\n")
    else response.body()!!.toString()

    Log.d("JsonObject", jsonObject.orEmpty())
    val aa = gson.fromJsonSafe(jsonObject.orEmpty(), V::class.java)
    Log.d("JsonObjectConvert", aa.toJson())
    return aa
}

fun processRequest(input: String): RequestModel {
    if (!isEncryptionEnabled) {
        val result = RequestModel(input) // Process without encryption
        isEncryptionEnabled = true       // Immediately enable encryption
        return result
    }
    return RequestModel(input.encrypt()) // Process with encryption enabled}
}

suspend inline fun <T : Any, reified V : Any> performMultiPartRequest(
    apiName: String, parts: List<MultipartBody.Part>, model: T? = null
): Resource<V> {
    return safeApiCall<V>(call = {
        val input = model.toPrettyJson(serializeNulls = true)
        showErrorLog<ApiLogger>("Request --> $apiName\n${input}")
        apiService.performMultiPartRequest(
            url = apiName,
            files = parts.toMutableList().apply {
                add(
                    model.toPayload().toMultiParts("payload")
                )
            }
        )
    })
}

//if we need to send two lists of images
suspend inline fun <T : Any, reified V : Any> performMultiPartRequestWithMultipleLists(
    apiName: String,
    list1: List<MultipartBody.Part>,
    list2: List<MultipartBody.Part>,
    model: T? = null
): Pair<Int, Resource<V>> {
    val prettyGson = GsonBuilder().serializeNulls().setPrettyPrinting().create()
    val input = prettyGson.toJson(model)
    showErrorLog<ApiLogger>("Request --> $apiName\n${input}")
    val apiFullName = "$baseUrl$apiName"
    var response: Response<JsonObject>? = null
    return try {
        response = apiService.performMultiPartRequestWithMultipleList(
            url = apiName,
            file1 = list1.toMutableList(),
            file2 = list2.toMutableList().apply { add(model.toPayload().toMultiParts("payload")) },
        )
        if (response.isSuccessful) {
            ApiLogger.logApiData(
                apiLog = ApiLog(
                    apiName = apiFullName.getApiEndpoint(),
                    url = apiFullName,
                    request = input.formatJsonString(),
                    response = response.body().toString().toPrettyJson(),
                    responseCode = response.code()
                )
            )
        } else {
            val errorModel = response.getErrorModel(gson).toErrorModel(response.code())
            ApiLogger.logApiData(
                apiLog = ApiLog(
                    apiName = apiFullName.getApiEndpoint(),
                    url = apiFullName,
                    request = input.formatJsonString(),
                    response = errorModel.toJson().toPrettyJson(),
                    responseCode = response.code()
                )
            )
            return Pair(
                response.code(),
                Resource.Invalid(errorModel)
            )
        }

        val obj: V = parseResponse(response)
            ?: return Pair(
                response.code(),
                NetworkErrorHandler.getGenericError(response.code())
            )

        return Pair(response.code(), Resource.Valid(obj))
    } catch (ex: Exception) {
        ex.printStackTrace()
        val apiErrorModel = response.getErrorModel(gson)
        ApiLogger.logApiData(
            apiLog = ApiLog(
                apiName = apiFullName.getApiEndpoint(),
                url = apiFullName,
                request = input.formatJsonString(),
                response = apiErrorModel.toJson().toPrettyJson(),
                responseCode = response?.code() ?: 0
            )
        )
        Pair(
            response?.code() ?: -1,
            Resource.Invalid(apiErrorModel.toErrorModel(response?.code()))
        )
    }
}


fun <T> T.toPayload(): String {
    val json = toPrettyJson(serializeNulls = true)
    return if (isEncryptionEnabled) json.encrypt() else json
}

fun String.toMultiParts(name: String): MultipartBody.Part {
    return MultipartBody.Part.createFormData(name, this)
}


suspend inline fun <reified T : Any> safeApiCall(
    crossinline call: suspend () -> Response<JsonObject>,
    decrypt: Boolean = isEncryptionEnabled,
): Resource<T> {
    return try {
        val response = call.invoke()

        val body = response.body()
        if (body == null) {
            getInvalid(response)
        } else {
            val jsonObject = body.toString()
            try {
                val obj = gson.fromJson(jsonObject, T::class.java)
                Resource.Valid(obj)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("Error", "Error")
                getInvalid(response)
            }
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
        Log.d("Error", "Error")
        Resource.Invalid(ErrorModel(description = getNetworkErrorMessage(ex)))
    }
}

inline fun <reified T> getInvalid(response: Response<JsonObject>): Resource<T> {
    Log.d("Error", "Error")
    return Resource.Invalid(errorModel = getErrorMessage(response))
}


fun <T> Gson.fromJsonSafe(json: String, klass: Class<T>): T? {
    return try {
        this.fromJson(json, klass)
    } catch (ex: Exception) {
        applyLogError("Exception${ex}")
        null
    }
}

fun String.getApiEndpoint(): String {
    return substringAfterLast("/")
}


fun String?.formatJsonString(): String {
    if (this == null) return ""
    return try {
        val json = JSONObject(this)
        json.toString(4)
    } catch (ex: Exception) {
        ""
    }
}


fun <T> Response<T>?.getErrorModel(gson: Gson): ApiErrorModel? {
    Log.d("Error", "Error Model")
    val error = this?.errorBody()?.string().formatJsonString().applyLogError("Error Response:\n")
    return gson.fromJsonSafe(error, ApiErrorModel::class.java)
}

inline fun <reified T> T.applyLogError(start: String = ""): T {
    return apply {
        showErrorLog("$start${toString()}")
    }
}

inline fun <reified T> T.showErrorLog(msg: String) {
    if (isDebug())
        Log.e(T::class.simpleName ?: "TAG", msg)
}

inline fun <reified T> showErrorLog(msg: String) {
    if (isDebug())
        Log.e(T::class.simpleName ?: "TAG", msg)
}

fun Any?.toPrettyJson(serializeNulls: Boolean = false) = this.toJson().toPrettyJson(serializeNulls)

fun Any?.toJson(): String = if (this == null) {
    ""
} else {
    Gson().toJson(this)
}

fun String.toPrettyJson(serializeNulls: Boolean = false): String {
    val gson = GsonBuilder().setPrettyPrinting()
        .apply { if (serializeNulls) serializeNulls() }
        .create()
    val je = JsonParser.parseString(this)
    return gson.toJson(je)
}