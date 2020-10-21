package mr.adkhambek.mvvm.usecase.sell

import mr.adkhambek.mvvm.model.SellItems
import mr.adkhambek.mvvm.model.SellResource

interface SellUseCase {
    suspend operator fun invoke(request: SellItems): SellResource
}