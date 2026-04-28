package com.sohaib.animehub.domain.errors

sealed class DomainError(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause) {
    data class Network(val original: Throwable? = null) : DomainError(cause = original)
    data class Server(val code: Int, val original: Throwable? = null) : DomainError(cause = original)
    data class Client(val code: Int, val original: Throwable? = null) : DomainError(cause = original)
    data class Unknown(val original: Throwable? = null) : DomainError(cause = original)
}