package com.example.network.utils

import okhttp3.internal.and
import java.nio.charset.StandardCharsets
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

var mobileEngine = "9ad3603d26814360ab333b31646d1c69"
var mobileEngineDummy = "9ad3603d26814360ab333b31646d1c69"
fun String.decrypt(): String {
    try {
        val cipher = Cipher.getInstance("AES_256/CBC/PKCS7Padding")
        val textParts = this.split(":").toTypedArray()
        val iv = textParts[0]
        val encryptedText = textParts[1]
        cipher.init(
            Cipher.DECRYPT_MODE,
            SecretKeySpec(mobileEngine.toByteArray(StandardCharsets.UTF_8), "AES_256"),
            IvParameterSpec(hexStringToByteArray(iv))
        )
        val cipherText = cipher.doFinal(hexStringToByteArray(encryptedText))
        return String(cipherText, StandardCharsets.UTF_8)
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    } catch (e: NoSuchPaddingException) {
        e.printStackTrace()
    } catch (e: InvalidAlgorithmParameterException) {
        e.printStackTrace()
    } catch (e: InvalidKeyException) {
        e.printStackTrace()
    } catch (e: BadPaddingException) {
        e.printStackTrace()
    } catch (e: IllegalBlockSizeException) {
        e.printStackTrace()
    }
    return this
}

fun String.encrypt(): String {
    val secureRandom = SecureRandom()
    val iv = ByteArray(16)
    secureRandom.nextBytes(iv)
    try {
        val cipher = Cipher.getInstance("AES_256/CBC/PKCS7Padding")
        cipher.init(
            Cipher.ENCRYPT_MODE,
            SecretKeySpec(mobileEngine.toByteArray(StandardCharsets.UTF_8), "AES"),
            IvParameterSpec(iv)
        )
        val cipherText = cipher.doFinal(this.toByteArray(StandardCharsets.UTF_8))
        val cipherTextHex: String = byteArrayToHexString(cipherText)
        return byteArrayToHexString(iv) + ":" + cipherTextHex
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    } catch (e: NoSuchPaddingException) {
        e.printStackTrace()
    } catch (e: InvalidAlgorithmParameterException) {
        e.printStackTrace()
    } catch (e: InvalidKeyException) {
        e.printStackTrace()
    } catch (e: BadPaddingException) {
        e.printStackTrace()
    } catch (e: IllegalBlockSizeException) {
        e.printStackTrace()
    }

    return this
}

fun hexStringToByteArray(s: String): ByteArray {
    val len = s.length
    val data = ByteArray(len / 2)
    var i = 0
    while (i < len) {
        data[i / 2] = ((Character.digit(s[i], 16) shl 4) + Character.digit(s[i + 1], 16)).toByte()
        i += 2
    }
    return data
}


fun byteArrayToHexString(bytes: ByteArray): String {
    val hexArray =
        charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
    val hexChars = CharArray(bytes.size * 2)
    var v: Int
    for (j in bytes.indices) {
        v = bytes[j] and 0xFF
        hexChars[j * 2] = hexArray[v ushr 4]
        hexChars[j * 2 + 1] = hexArray[v and 0x0F]
    }
    return String(hexChars)
}