package mr.adkhambek.mvvm.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import mr.adkhambek.mvvm.usecase.load.LoadProductsUseCase
import mr.adkhambek.mvvm.usecase.load.LoadProductsUseCaseImpl
import mr.adkhambek.mvvm.usecase.sell.SellUseCase
import mr.adkhambek.mvvm.usecase.sell.SellUseCaseImpl


@Module
@InstallIn(ActivityRetainedComponent::class)
interface UseCaseModule {

    @Binds
    fun bindLoadProductsUseCase(binder: LoadProductsUseCaseImpl): LoadProductsUseCase

    @Binds
    fun bindSellUseCase(binder: SellUseCaseImpl): SellUseCase
}