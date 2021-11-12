package mr.adkhambek.mvvm.datasource.network


import mr.adkhambek.mvvm.model.ItemResponse
import retrofit2.http.GET


interface MainAPI {

    @GET("service/v2/upcomingGuides/")
    suspend fun upcomingGuides(): ItemResponse

}