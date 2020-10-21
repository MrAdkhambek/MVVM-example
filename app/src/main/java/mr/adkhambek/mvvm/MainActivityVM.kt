package mr.adkhambek.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mr.adkhambek.mvvm.model.ProductDTO
import mr.adkhambek.mvvm.model.Products
import mr.adkhambek.mvvm.network.BaseResponse
import mr.adkhambek.mvvm.network.MainAPI


class MainActivityVM(
    private val api: MainAPI
) : ViewModel() {

    private val mProductsLiveData: MutableLiveData<Products> = MutableLiveData()
    val productsLiveData: LiveData<Products> get() = mProductsLiveData

    init {
        viewModelScope.launch {
            val products: BaseResponse<List<ProductDTO>> = api.loadProducts()
            products.data?.let(mProductsLiveData::setValue)
        }
    }
}