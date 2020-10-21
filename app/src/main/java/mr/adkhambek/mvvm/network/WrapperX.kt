package mr.adkhambek.mvvm.network

import mr.adkhambek.mvvm.model.ResourceUI
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response


@DslMarker
annotation class WrapperDsl

class NetworkException(message: String) : Exception(message)

@WrapperDsl
suspend fun <T> wrapperX(
    body: suspend () -> Response<BaseResponse<T>>
): ResourceUI<T> {
    return try {
        val response: Response<BaseResponse<T>> = body()
        checkStatus(response)
    } catch (e: Exception) {
        when (e) {
            is NullPointerException -> ResourceUI.Error(e)
            else -> ResourceUI.Error(e)
        }
    }
}

private fun <T> checkStatus(response: Response<BaseResponse<T>>): ResourceUI<T> {
    val body: BaseResponse<T>? = response.body()
    val data: T? = body?.data
    return when (response.code()) {
        in 200..299 -> {
            if (data != null) ResourceUI.Resource(data, response.code())
            else ResourceUI.Error(NullPointerException("Response data must not be null"))
        }
        else -> {
            val message: String = handleError(response.errorBody())
            ResourceUI.Error(NetworkException(message))
        }
    }
}

private fun handleError(body: ResponseBody?): String {
    val tempError = """{ "errorMessage" = "Some Error from network" }"""
    val byteArray: ByteArray = body?.bytes() ?: tempError.toByteArray()
    return try {
        JSONObject(String(byteArray)).getString("errorMessage")
    } catch (e: JSONException) {
        "Some Error from network"
    }
}