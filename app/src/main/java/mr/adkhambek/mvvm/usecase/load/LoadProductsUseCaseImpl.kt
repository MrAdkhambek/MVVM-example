package mr.adkhambek.mvvm.usecase.load

import mr.adkhambek.mvvm.model.ProductResource
import mr.adkhambek.mvvm.network.wrapperX
import mr.adkhambek.mvvm.repository.MainRepository
import javax.inject.Inject


class LoadProductsUseCaseImpl @Inject constructor(
    private val repository: MainRepository
) : LoadProductsUseCase {

    override suspend fun invoke(): ProductResource = wrapperX {
        repository.loadProducts()
    }
}