package mr.adkhambek.mvvm.model


import mr.adkhambek.mvvm.network.BaseResponse
import retrofit2.Response


typealias SellResult = List<SellDTO>

typealias SellResource = ResourceUI<SellResult>
typealias SellResponse = Response<BaseResponse<SellResult>>


data class SellDTO(

    val id: Int,
    val sell: Boolean
)

data class SellEvent(
    val list: SellResult
)

typealias SellItems = List<SellItem>

data class SellItem(
    val id: Int,
    val count: Int
)