package com.example.network.api

import com.example.network.RequestModel
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Url


interface ApiService {
    @POST
    suspend fun performPostRequest(
        @Url url: String,
        @Body inputModel: RequestModel,
    ): Response<JsonObject>

    @Multipart
    @POST
    suspend fun performMultiPartRequest(
        @Url url: String,
        @Part files: List<MultipartBody.Part>,
    ): Response<JsonObject>

    @Multipart
    @POST
    suspend fun performMultiPartRequestWithMultipleList(
        @Url url: String,
        @Part file1: List<MultipartBody.Part>,
        @Part file2: List<MultipartBody.Part>
    ): Response<JsonObject>
}