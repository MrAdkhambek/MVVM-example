package mr.adkhambek.mvvm.network

import mr.adkhambek.mvvm.model.Products
import mr.adkhambek.mvvm.model.SellItems
import mr.adkhambek.mvvm.model.SellResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface MainAPI {

    @GET("product/all")
    suspend fun loadProducts(): BaseResponse<Products>

    @POST("product/sell")
    suspend fun sellProducts(@Body body: SellItems): BaseResponse<SellResult>
}