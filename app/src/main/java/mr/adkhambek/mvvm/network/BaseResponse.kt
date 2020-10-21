package mr.adkhambek.mvvm.network

import com.google.gson.annotations.SerializedName


data class BaseResponse<T>(

    val data: T?,

    val message: String,

    @SerializedName("status_code")
    val statusCode: Int = 200
)