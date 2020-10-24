package mr.adkhambek.mvvm

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import mr.adkhambek.mvvm.model.Products
import mr.adkhambek.mvvm.usecase.load.LoadProductsUseCase
import mr.adkhambek.mvvm.usecase.sell.SellUseCase


class MainActivityVM(
    private val sellUseCase: SellUseCase,
    private val loadProductsUseCase: LoadProductsUseCase
) : ViewModel() {

    private val mProductsLiveData: MutableLiveData<Products> = MutableLiveData()
    val productsLiveData: LiveData<Products> get() = mProductsLiveData

    init {
        viewModelScope.launch {

        }
    }
}

class MainActivityVMF(
    private val sellUseCase: SellUseCase,
    private val loadProductsUseCase: LoadProductsUseCase,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityVM(sellUseCase, loadProductsUseCase) as T
    }
}