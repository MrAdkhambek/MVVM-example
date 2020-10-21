package mr.adkhambek.mvvm.usecase.load

import mr.adkhambek.mvvm.model.ProductResource


interface LoadProductsUseCase {
    suspend operator fun invoke(): ProductResource
}