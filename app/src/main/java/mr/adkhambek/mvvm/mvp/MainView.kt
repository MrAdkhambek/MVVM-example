package mr.adkhambek.mvvm.mvp

import mr.adkhambek.mvvm.model.ProductDTO
import mr.adkhambek.mvvm.model.Products
import mr.adkhambek.mvvm.model.SellResult

interface BaseView

interface BasePresenter<V : BaseView> {
    var view: V?
    fun onAttach(v: V)
    fun onDetach()
}

interface MainView : BaseView {

    fun swipeState(isLoading: Boolean)

    fun showProducts(products: Products)

    fun showResult(sellResult: SellResult)

    fun showErrorSnackbar(message: String)

    fun showBasketElementsSize(size: Int)
}


interface MainPresenter : BasePresenter<MainView> {

    fun onSwipeAction()

    fun onSellAction()

    fun selectProduct(product: ProductDTO)
}

interface Model {

    suspend fun loadProducts()

    suspend fun sellProducts()
}