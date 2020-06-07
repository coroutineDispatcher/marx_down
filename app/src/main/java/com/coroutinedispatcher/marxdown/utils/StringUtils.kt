package com.coroutinedispatcher.marxdown.utils

internal fun String.removeCharAt(index: Int): String {
    return this.substring(0, index) + this.substring(index + 1)
}