package mr.adkhambek.mvvm.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response


typealias Items = List<ItemDTO>
typealias ItemResponse = Response<BaseResponse<Items>>

@Serializable
data class BaseResponse<T>(

    @SerialName("data")
    val data: T
)

@Serializable
data class ItemDTO(

    @SerialName("endDate")
    val endDate: String,

    @SerialName("icon")
    val icon: String,

    @SerialName("loginRequired")
    val loginRequired: Boolean,

    @SerialName("name")
    val name: String,

    @SerialName("objType")
    val objType: String,

    @SerialName("startDate")
    val startDate: String,

    @SerialName("url")
    val url: String,
)