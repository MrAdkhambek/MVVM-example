package mr.adkhambek.mvvm.usecase.sell

import mr.adkhambek.mvvm.model.SellItems
import mr.adkhambek.mvvm.model.SellResource
import mr.adkhambek.mvvm.network.wrapperX
import mr.adkhambek.mvvm.repository.MainRepository
import javax.inject.Inject

class SellUseCaseImpl @Inject constructor(
    private val repository: MainRepository
) : SellUseCase {

    override suspend fun invoke(request: SellItems): SellResource = wrapperX {
        repository.sellProducts(request)
    }
}