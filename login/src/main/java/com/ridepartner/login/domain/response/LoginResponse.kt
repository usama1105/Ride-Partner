package com.ridepartner.login.domain.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class LoginResponse(
    val message: String,
    val user: User,
    val token: String
)

data class User(
    val preferences: UserPreferences,
    val pickupLocation: Location,
    val dropoffLocation: Location,
    @SerialName("_id")
    val id: String,
    val email: String,
    val username: String,
    val phone: String,
    val profileImage: String? = null,
    val isEmailVerified: Boolean,
    @SerialName("login_via")
    val loginVia: String,
    val isBlocked: Boolean,
    val role: String,
    val isProfileComplete: Boolean,
    val lastLoginPlatform: String,
    val lastLoginAt: String,
    val otps: List<Otp> = emptyList(),
    val loginAttempts: List<LoginAttempt> = emptyList(),
    val createdAt: String,
    val updatedAt: String,
    @SerialName("__v")
    val version: Int
)

@Serializable
data class UserPreferences(
    val smoking: Boolean,
    val femaleDriver: Boolean,
    val music: Boolean
)

@Serializable
data class Location(
    val address: String
)

@Serializable
data class Otp(
    val code: String,
    val purpose: String,
    val expiresAt: String,
    val isUsed: Boolean,
    @SerialName("_id")
    val id: String,
    val createdAt: String
)

@Serializable
data class LoginAttempt(
    val platform: String,
    val successful: Boolean,
    val ipAddress: String,
    @SerialName("_id")
    val id: String,
    val timestamp: String
)