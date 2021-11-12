package mr.adkhambek.mvvm.ui.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import mr.adkhambek.mvvm.model.ItemDTO
import mr.adkhambek.mvvm.repository.MainRepository
import mr.adkhambek.mvvm.utils.Result
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    fun saveAndLoad(): Flow<Result<List<ItemDTO>>> =
        repository.loadAndSave()
}