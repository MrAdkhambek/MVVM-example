package mr.adkhambek.mvvm.di.datasource.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mr.adkhambek.mvvm.datasource.network.MainAPI
import retrofit2.Retrofit
import javax.inject.Provider


@[Module InstallIn(SingletonComponent::class)]
object ApiModule {

    @Provides
    fun provideAuthAPI(
        retrofitProvider: Provider<Retrofit>,
    ): MainAPI = retrofitProvider.get().create(MainAPI::class.java)
}