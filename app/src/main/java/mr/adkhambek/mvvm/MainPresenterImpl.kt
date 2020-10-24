package mr.adkhambek.mvvm

import kotlinx.coroutines.*
import mr.adkhambek.mvvm.model.ProductDTO
import mr.adkhambek.mvvm.model.SellItem
import mr.adkhambek.mvvm.model.SellItems
import mr.adkhambek.mvvm.model.SellResult
import mr.adkhambek.mvvm.mvp.MainPresenter
import mr.adkhambek.mvvm.mvp.MainView
import mr.adkhambek.mvvm.network.BaseResponse
import mr.adkhambek.mvvm.network.MainAPI
import retrofit2.HttpException


class MainPresenterImpl(
    private val api: MainAPI
) : MainPresenter {

    override var view: MainView? = null

    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val basket: MutableList<ProductDTO> = arrayListOf()

    override fun onSwipeAction() {
        view?.swipeState(true)
        scope.launch {
            try {
                val products: BaseResponse<List<ProductDTO>> = api.loadProducts()
                products.data?.let {
                    view?.showProducts(it)
                }

            } catch (e: HttpException) {
                view?.showErrorSnackbar(e.message())
            } catch (e: Exception) {
                view?.showErrorSnackbar("Some error")
            } finally {
                view?.swipeState(false)
            }
        }
    }

    override fun onSellAction() {
        view?.swipeState(true)
        scope.launch {
            try {
                val sellItems: SellItems = basket.groupBy { it.id }.map {
                    SellItem(it.key, it.value.size)
                }

                val sellResult: BaseResponse<SellResult> = api.sellProducts(sellItems)
                if (sellResult.statusCode == 200) sellResult.data?.let { list ->
                    view?.showResult(list)
                }
                else throw Exception("${sellResult.message} ${sellResult.statusCode}")

            } catch (e: Exception) {
                view?.showErrorSnackbar("Some error ${e.message}")
            } finally {
                basket.clear()
                view?.showBasketElementsSize(basket.size)
                view?.swipeState(false)
            }
        }
    }

    override fun selectProduct(product: ProductDTO) {
        scope.launch {
            basket.add(product)
            view?.showBasketElementsSize(basket.size)
        }
    }


    override fun onAttach(v: MainView) {
        this.view = v
    }

    override fun onDetach() {
        scope.coroutineContext.cancel()
        view = null
    }
}