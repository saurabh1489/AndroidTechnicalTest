package com.bridge.androidtechnicaltest.vo

import com.bridge.androidtechnicaltest.vo.Status.*

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

class Resource<out T>(val status: Status, val data: T?, val message: String? = null) {
    companion object {
        fun <T> success(data: T?): Resource<T> = Resource(SUCCESS, data, null)

        fun <T> error(message: String?, data: T?): Resource<T> = Resource(ERROR, data, message)

        fun <T> loading(data: T?): Resource<T> = Resource(LOADING, data, null)
    }
}
