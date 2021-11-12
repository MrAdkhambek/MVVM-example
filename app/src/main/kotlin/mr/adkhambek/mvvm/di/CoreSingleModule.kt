package mr.adkhambek.mvvm.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mr.adkhambek.mvvm.utils.ResponseWrapperImpl
import mr.adkhambek.domain.util.resource.ResourceManager
import mr.adkhambek.mvvm.utils.ResourceManagerImpl
import mr.adkhambek.mvvm.utils.ResponseWrapper
import javax.inject.Singleton


@[Module InstallIn(SingletonComponent::class)]
interface CoreSingleModule {

    @[Binds Singleton]
    fun bindResourceManager(binder: ResourceManagerImpl): ResourceManager

    @[Binds Singleton]
    fun bindResponseWrapper(binder: ResponseWrapperImpl): ResponseWrapper
}