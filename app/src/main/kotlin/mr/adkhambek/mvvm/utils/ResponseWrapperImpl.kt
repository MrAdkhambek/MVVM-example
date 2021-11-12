package mr.adkhambek.mvvm.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import mr.adkhambek.domain.util.resource.ResourceManager
import mr.adkhambek.mvvm.R
import mr.adkhambek.mvvm.exception.NetworkException
import mr.adkhambek.mvvm.model.BaseResponse
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject


class ResponseWrapperImpl @Inject constructor(
    private val resourceManager: ResourceManager,
) : ResponseWrapper {

    override val errorsFlow: MutableSharedFlow<Throwable> = MutableSharedFlow()
    override val responseFlow: MutableSharedFlow<Response<*>> = MutableSharedFlow()
    override val resourceUiFlow: MutableSharedFlow<Result<*>> = MutableSharedFlow()

    override suspend fun <T> proceed(
        body: suspend () -> Response<BaseResponse<T>>,
    ): Result<T> {
        return try {
            val response: Response<BaseResponse<T>> = body()
            responseFlow.emit(response)
            convertResourceUI(response)
        } catch (e: Exception) {
            errorsFlow.emit(e)
            when (e) {
                is NullPointerException -> Result.Error(e)
                else -> Result.Error(e)
            }
        }.also { response ->
            resourceUiFlow.emit(response)
        }
    }

    override fun <T> proceed(
        fromNetwork: suspend () -> Response<BaseResponse<T>>,
        fromCache: suspend () -> T,
        chainNetwork: suspend (response: Result<T>) -> Result<T>,
    ): Flow<Result<T>> = flow {
        emit(Result.Loading)
        val cacheResource: Result<T> = Result.of { fromCache() }

        emit(cacheResource)
        val networkResource: Result<T> = proceed(fromNetwork)
        emit(chainNetwork(networkResource))
    }

    private fun <T> convertResourceUI(response: Response<BaseResponse<T>>): Result<T> {
        val body: BaseResponse<T>? = response.body()
        val data: T? = body?.data

        return when (response.code()) {
            in 200..299 -> {
                if (data != null) Result.Success(data)
                else Result.Error(NullPointerException(resourceManager.getString(R.string.error_data_null)))
            }
            else -> {
                val message: String = handleError(response.errorBody())
                Result.Error(NetworkException(message, response.code()))
            }
        }
    }

    private fun handleError(body: ResponseBody?): String {
        val defaultErrorMessage = resourceManager.getString(R.string.error_network)
        val defaultError = resourceManager.getString(R.string.error_network_json, defaultErrorMessage)

        val byteArray: String = body?.bytes()?.let(::String) ?: defaultError

        return try {
            JSONObject(byteArray).getString("message")
        } catch (e: JSONException) {
            defaultErrorMessage
        }
    }
}
