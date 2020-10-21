package mr.adkhambek.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mr.adkhambek.mvvm.model.ProductDTO
import mr.adkhambek.mvvm.model.Products


class MainActivityVM : ViewModel() {

    private val mProductsLiveData: MutableLiveData<Products> = MutableLiveData()
    val productsLiveData: LiveData<Products> get() = mProductsLiveData

    init {
        mProductsLiveData.value = arrayListOf(
            ProductDTO(
                1,
                100,
                "Apple",
                "https://i5.walmartimages.ca/images/Enlarge/094/514/6000200094514.jpg",
                1000.0
            )
        )
    }
}