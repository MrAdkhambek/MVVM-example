package mr.adkhambek.mvvm.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mr.adkhambek.mvvm.model.ProductResponse
import mr.adkhambek.mvvm.model.SellItems
import mr.adkhambek.mvvm.model.SellResponse
import mr.adkhambek.mvvm.network.MainAPI
import javax.inject.Inject


@ActivityRetainedScoped
class MainRepository @Inject constructor(
    private val mainAPI: MainAPI
) {

    suspend fun loadProducts(): ProductResponse = withContext(Dispatchers.IO) {
        mainAPI.loadProducts2()
    }

    suspend fun sellProducts(request: SellItems): SellResponse = withContext(Dispatchers.IO) {
        mainAPI.sellProducts2(request)
    }
}

class UserRepository {

}
