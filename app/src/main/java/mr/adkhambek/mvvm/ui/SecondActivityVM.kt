package mr.adkhambek.mvvm.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mr.adkhambek.mvvm.model.ProductResource
import mr.adkhambek.mvvm.model.ResourceUI
import mr.adkhambek.mvvm.model.SellEvent
import mr.adkhambek.mvvm.network.SocketUtil
import mr.adkhambek.mvvm.usecase.load.LoadProductsUseCase


class SecondActivityVM @ViewModelInject constructor(
    private val loadProductsUseCase: LoadProductsUseCase,
    private val socket: SocketUtil
) : ViewModel() {

    val sellLiveData: LiveData<SellEvent> = socket.EventLiveData(SellEvent::class.java, "sell")

    private val mProductsLiveData: MutableLiveData<ProductResource> = MutableLiveData()
    val productsLiveData: LiveData<ProductResource> get() = mProductsLiveData

    init {
        socket.connect()
        onLoadProducts()
    }

    fun onLoadProducts() {
        viewModelScope.launch {
            mProductsLiveData.apply {
                value = ResourceUI.Loading
                value = loadProductsUseCase()
            }
        }
    }

    override fun onCleared() {
        socket.disconnect()
        super.onCleared()
    }
}