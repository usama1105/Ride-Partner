package com.example.network.api

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Url


interface ApiService {
    @POST
    suspend fun performPostRequest(
        @Url url: String,
        @Body inputModel: Any,
        @Header("x-platform-id") platformId: String,
    ): Response<JsonObject>

    @Multipart
    @POST
    suspend fun performMultiPartRequest(
        @Url url: String,
        @Header("x-platform-id") platformId: String,
        @Part files: List<MultipartBody.Part>,
    ): Response<JsonObject>

    @Multipart
    @POST
    suspend fun performMultiPartRequestWithMultipleList(
        @Url url: String,
        @Header("x-platform-id") platformId: String,
        @Part file1: List<MultipartBody.Part>,
        @Part file2: List<MultipartBody.Part>
    ): Response<JsonObject>
}