package com.beershop.adgaon.base.repository

sealed class Resource<T>(
    val type: T? = null,
    val message: String? = null
) {
    class Loading<T>(type: T? = null) : Resource<T>(type)
    class Success<T>(type: T) : Resource<T>(type)
    class Error<T>(type: T? = null, message: String = "") : Resource<T>(type, message)
}