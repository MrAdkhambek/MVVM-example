package mr.adkhambek.mvvm.model

import mr.adkhambek.mvvm.network.BaseResponse
import retrofit2.Response

typealias Products = List<ProductDTO>

typealias ProductResource = ResourceUI<Products>
typealias ProductResponse = Response<BaseResponse<Products>>


data class ProductDTO(

    val id: Int,

    var count: Int,

    val name: String,

    val image: String,

    val price: Double
)