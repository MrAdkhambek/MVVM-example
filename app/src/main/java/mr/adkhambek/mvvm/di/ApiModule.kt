package mr.adkhambek.mvvm.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import mr.adkhambek.mvvm.network.MainAPI
import retrofit2.Retrofit


@Module
@InstallIn(ActivityRetainedComponent::class)
object ApiModule {

    @Provides
    @ActivityRetainedScoped
    fun provideMainAPI(
        retrofit: Retrofit
    ): MainAPI = retrofit.create(MainAPI::class.java)

}