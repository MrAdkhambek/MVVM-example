package mr.adkhambek.mvvm.utils

import androidx.annotation.CheckResult
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract


@DslMarker
annotation class ResourceDSL

@ResourceDSL
sealed class Result<out T> {

    object Loading : Result<Nothing>()
    data class Error(val error: Throwable) : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()

    companion object {

        @ResourceDSL
        inline fun <T> of(block: () -> T): Result<T> {
            return try {
                Success(block())
            } catch (throwable: Throwable) {
                Error(throwable)
            }
        }
    }
}


@ResourceDSL
inline fun <T, R> Result<T>.bind(block: (T) -> Result<R>): Result<R> {
    return when (this) {
        is Result.Error -> this
        Result.Loading -> Result.Loading
        is Result.Success -> block(data)
    }
}

@ResourceDSL
inline fun <T> Result<T>.then(block: () -> T): Result<T> {
    return when (this) {
        is Result.Success -> Result.of { block() }
        else -> this
    }
}

@ResourceDSL
inline fun <T> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    return when (this) {
        is Result.Success -> apply { action(data) }
        else -> this
    }
}

@ResourceDSL
inline fun <T> Result<T>.onError(action: (Throwable) -> Unit): Result<T> {
    return when (this) {
        is Result.Error -> apply { action(this.error) }
        else -> this
    }
}

@ResourceDSL
inline fun <T> Result<T>.onLoading(action: (Result.Loading) -> Unit): Result<T> {
    return when (this) {
        Result.Loading -> apply { action(Result.Loading) }
        else -> this
    }
}

@ResourceDSL
@CheckResult
inline fun <T> Result<T>.getOrElse(
    elseBlock: () -> T,
): T {
    return when (this) {
        is Result.Success -> this.data as T
        else -> elseBlock()
    }
}