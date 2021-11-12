package mr.adkhambek.mvvm.utils

import kotlinx.coroutines.flow.Flow
import mr.adkhambek.mvvm.model.BaseResponse
import retrofit2.Response

@DslMarker
annotation class WrapperDsl

interface ResponseWrapper {

    val errorsFlow: Flow<Throwable>
    val responseFlow: Flow<Response<*>>
    val resourceUiFlow: Flow<Result<*>>

    @WrapperDsl
    suspend fun <T> proceed(
        body: suspend () -> Response<BaseResponse<T>>,
    ): Result<T>

    @WrapperDsl
    fun <T> proceed(
        fromNetwork: suspend () -> Response<BaseResponse<T>>,
        fromCache: suspend () -> T,
        chainNetwork: suspend (response: Result<T>) -> Result<T> = { it },
    ): Flow<Result<T>>
}
