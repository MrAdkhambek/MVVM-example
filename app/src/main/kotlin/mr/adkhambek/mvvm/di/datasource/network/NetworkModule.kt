package mr.adkhambek.mvvm.di.datasource.network


import android.content.Context
import android.os.Looper
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mocklets.pluto.PlutoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import mr.adkhambek.mvvm.BuildConfig
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Converter
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Provider
import javax.inject.Singleton


@[Module InstallIn(SingletonComponent::class)]
object NetworkModule {

    @[Provides Singleton]
    fun provideChuckerCollector(
        @ApplicationContext context: Context,
    ): PlutoInterceptor = PlutoInterceptor()

    @[Provides Singleton]
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor {
        Timber.tag("NETWORK").d(it)
    }.apply {
        setLevel(if (BuildConfig.DEBUG) BODY else NONE)
    }

    @[Provides Singleton]
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }


    @[Provides Singleton ExperimentalSerializationApi]
    fun provideFactory(json: Json): Converter.Factory {
        val contentType = "application/json".toMediaType()
        return json.asConverterFactory(contentType)
    }

    @[Provides Singleton]
    fun provideRetrofit(
        clientProvider: Provider<OkHttpClient>,
        factoryProvider: Provider<Converter.Factory>,
    ): Retrofit {
        require(!BuildConfig.DEBUG || Looper.myLooper() != Looper.getMainLooper()) { "Retrofit want to create Main thread" }

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(clientProvider.get())
            .addConverterFactory(factoryProvider.get())
            .build()
    }

    @[Provides Singleton]
    fun provideClient(
        plutoInterceptor: PlutoInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient().newBuilder()
        .retryOnConnectionFailure(false)
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(plutoInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .build()

}