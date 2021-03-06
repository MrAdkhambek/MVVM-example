package mr.adkhambek.mvvm

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.load
import com.adam.leo.LeoAdapter
import com.adam.leo.recycler.setupAdapter
import com.andremion.counterfab.CounterFab
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import mr.adkhambek.mvvm.model.ProductDTO
import mr.adkhambek.mvvm.model.Products
import mr.adkhambek.mvvm.model.SellResult
import mr.adkhambek.mvvm.mvp.MainPresenter
import mr.adkhambek.mvvm.mvp.MainView
import mr.adkhambek.mvvm.ui.SecondActivity
import mr.adkhambek.mvvm.utils.getLoadProductsUseCase
import mr.adkhambek.mvvm.utils.getMainAPI
import mr.adkhambek.mvvm.utils.getMainRepository
import mr.adkhambek.mvvm.utils.getSellUseCase


@AndroidEntryPoint
class MainActivity :
    AppCompatActivity(R.layout.activity_main),
    MainView {

    private lateinit var leoAdapter: LeoAdapter<ProductDTO>
    private lateinit var presenter: MainPresenter

//    val viewModel: MainActivityVM by viewModels {
//        val mainAPI = getMainAPI()
//        val mainRepository = getMainRepository(mainAPI)
//        MainActivityVMF(
//            getSellUseCase(mainRepository),
//            getLoadProductsUseCase(mainRepository)
//        )
//    }

    private lateinit var fab: CounterFab
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainAPI = getMainAPI()

        presenter = MainPresenterImpl(mainAPI)
        presenter.onAttach(this)

        val rv: RecyclerView = findViewById(R.id.recyclerView)
        setupRecyclerView(rv)

        fab = findViewById(R.id.counter_fab)
        fab.setOnClickListener {
            presenter.onSellAction()
        }

        fab.setOnLongClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
            true
        }

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener { presenter.onSwipeAction() }
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    private fun setupRecyclerView(recycler: RecyclerView): Unit = with(recycler) {
        layoutManager = LinearLayoutManager(context)
        leoAdapter = setupAdapter(
            R.layout.item_product
        ) {

            val productImage: AppCompatImageView = findViewById(R.id.image_view)
            val productName: AppCompatTextView = findViewById(R.id.product_name)
            val productPrice: AppCompatTextView = findViewById(R.id.product_price)
            val productCount: AppCompatTextView = findViewById(R.id.product_count)

            bind { view, _, item ->
                productImage.load(item.image) {
                    lifecycle(this@MainActivity)
                }

                productName.text = item.name
                productCount.text = item.count.toString()
                productPrice.text = item.price.toString()

                view.setOnClickListener {
                    presenter.selectProduct(item)
                }
            }
        }
    }

    override fun swipeState(isLoading: Boolean) {
        swipeRefreshLayout.isRefreshing = isLoading
    }

    override fun showProducts(products: Products) {
        leoAdapter.setList(products)
    }

    override fun showResult(sellResult: SellResult) {
        val result = sellResult.groupBy {
            it.sell
        }

        val successItemsCount = result[true]?.size ?: 0
        val failedItemsCount = result[false]?.size ?: 0

        Toast.makeText(
            this, """
            Success sell count : $successItemsCount
            Fail sell count : $failedItemsCount
        """.trimIndent(), Toast.LENGTH_SHORT
        ).show()
    }

    override fun showErrorSnackbar(message: String) {
        swipeRefreshLayout.let { view ->
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun showBasketElementsSize(size: Int) {
        fab.count = size
    }
}