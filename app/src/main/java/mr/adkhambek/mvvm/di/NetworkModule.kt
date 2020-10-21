package mr.adkhambek.mvvm.di


import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import mr.adkhambek.mvvm.constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context,
        chuckerCollector: ChuckerCollector
    ): ChuckerInterceptor = ChuckerInterceptor(
        context = context,
        collector = chuckerCollector
    )

    @Provides
    @Singleton
    fun provideChuckerCollector(
        @ApplicationContext context: Context
    ): ChuckerCollector = ChuckerCollector(
        context = context,
        showNotification = true,
        retentionPeriod = RetentionManager.Period.ONE_WEEK
    )


    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor {
        Log.d("NETWORK", it)
    }.apply {
        setLevel(BODY)
    }


    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()


    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson,
        client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    @Provides
    @Singleton
    fun provideClient(
        chuckerInterceptor: ChuckerInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient().newBuilder()
        .retryOnConnectionFailure(false)
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(chuckerInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}