package mr.adkhambek.mvvm.repository

import dagger.Lazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import mr.adkhambek.mvvm.datasource.db.entity.item.ItemDao
import mr.adkhambek.mvvm.datasource.db.mapper.ItemMapper
import mr.adkhambek.mvvm.datasource.network.MainAPI
import mr.adkhambek.mvvm.model.ItemDTO
import mr.adkhambek.mvvm.model.Items
import mr.adkhambek.mvvm.utils.ResponseWrapper
import mr.adkhambek.mvvm.utils.Result
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MainRepository @Inject constructor(
    private val itemDao: ItemDao,
    private val mapper: ItemMapper,
    private val mainAPI: Lazy<MainAPI>,
    private val responseWrapper: ResponseWrapper
) {

    fun loadAndSave(): Flow<Result<Items>> = responseWrapper.proceed({
        return@proceed mainAPI.get().upcomingGuides()
    }, {
        return@proceed itemDao.getItems().map(mapper::mapRT)
    }, { result ->
        when (result) {
            is Result.Success -> itemDao.insert(result.data.map(mapper::mapTR))
            else -> Unit
        }
        result
    }).flowOn(Dispatchers.IO)
}