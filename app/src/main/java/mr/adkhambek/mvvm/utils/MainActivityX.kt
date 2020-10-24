package mr.adkhambek.mvvm.utils

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import mr.adkhambek.mvvm.MainActivity
import mr.adkhambek.mvvm.di.ApiModule
import mr.adkhambek.mvvm.di.NetworkModule
import mr.adkhambek.mvvm.network.MainAPI
import mr.adkhambek.mvvm.repository.MainRepository
import mr.adkhambek.mvvm.usecase.load.LoadProductsUseCase
import mr.adkhambek.mvvm.usecase.load.LoadProductsUseCaseImpl
import mr.adkhambek.mvvm.usecase.sell.SellUseCase
import mr.adkhambek.mvvm.usecase.sell.SellUseCaseImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


fun MainActivity.getMainAPI(): MainAPI {
    val chuckCollector: ChuckerCollector = NetworkModule.provideChuckerCollector(this)
    val chuck: ChuckerInterceptor = NetworkModule.provideChuckerInterceptor(this, chuckCollector)

    val httpLoggingInterceptor: HttpLoggingInterceptor = NetworkModule.provideHttpLoggingInterceptor()

    val gson: Gson = NetworkModule.provideGson()
    val okHttpClient: OkHttpClient = NetworkModule.provideClient(chuck, httpLoggingInterceptor)

    val retrofit: Retrofit = NetworkModule.provideRetrofit(gson, okHttpClient)

    return ApiModule.provideMainAPI(retrofit)
}

fun MainActivity.getMainRepository(mainAPI: MainAPI): MainRepository {
    return MainRepository(mainAPI)
}

fun MainActivity.getSellUseCase(mainRepository: MainRepository): SellUseCase {
    return SellUseCaseImpl(mainRepository)
}

fun MainActivity.getLoadProductsUseCase(mainRepository: MainRepository): LoadProductsUseCase {
    return LoadProductsUseCaseImpl(mainRepository)
}
