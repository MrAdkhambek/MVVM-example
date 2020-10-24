package mr.adkhambek.mvvm.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.adam.leo.LeoAdapter
import com.adam.leo.recycler.setupAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import mr.adkhambek.mvvm.R
import mr.adkhambek.mvvm.model.ProductDTO
import mr.adkhambek.mvvm.model.ProductResource
import mr.adkhambek.mvvm.model.ResourceUI
import mr.adkhambek.mvvm.model.SellEvent
import mr.adkhambek.mvvm.utils.LocationUtil


@AndroidEntryPoint
class SecondActivity : AppCompatActivity(R.layout.activity_second) {

    private val viewModel: SecondActivityVM by viewModels()

    private val sellObserver: Observer<SellEvent> = Observer { sellEvent ->
        viewModel.onLoadProducts()

        val result = sellEvent.list.groupBy {
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

    private var leoAdapter: LeoAdapter<ProductDTO>? = null
    private val productsObserver: Observer<ProductResource> = Observer { resourcesUI ->
//        val isLoading = resourcesUI is ResourceUI.Loading
        when (resourcesUI) {
            is ResourceUI.Resource -> {
                leoAdapter?.setList(resourcesUI.data)
            }

            is ResourceUI.Error ->
                showSnackbar(
                    resourcesUI.error.message ?: getString(R.string.sample_error)
                )

            is ResourceUI.Loading -> {

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.apply {
            sellLiveData.observe(this@SecondActivity, sellObserver)
            productsLiveData.observe(this@SecondActivity, productsObserver)
        }

        Log.d("SecondActivityVM", viewModel.toString())

        val rv: RecyclerView = findViewById(R.id.recyclerView)
        setupRecyclerView(rv)
    }

    override fun onResume() {
        super.onResume()
//        locationUtil?.onResume()
    }

    override fun onPause() {
        super.onPause()
//        locationUtil?.onPause()
    }

    private fun setupRecyclerView(recycler: RecyclerView): Unit = with(recycler) {
        layoutManager = LinearLayoutManager(context)
        leoAdapter = setupAdapter(
            R.layout.item_product,
            ProductDiffUtilCallBack
        ) {

            val productImage: AppCompatImageView = findViewById(R.id.image_view)
            val productName: AppCompatTextView = findViewById(R.id.product_name)
            val productPrice: AppCompatTextView = findViewById(R.id.product_price)
            val productCount: AppCompatTextView = findViewById(R.id.product_count)

            bind { _, _, item ->
                productImage.load(item.image) {
                    lifecycle(this@SecondActivity)
                }

                productName.text = item.name
                productCount.text = item.count.toString()
                productPrice.text = item.price.toString()
            }
        }
    }

    private fun showSnackbar(mes: String) {
        val view = findViewById<View>(R.id.container)
        Snackbar.make(view, mes, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.reply) {
                viewModel.onLoadProducts()
            }.show()
    }
}

private val ProductDiffUtilCallBack: DiffUtil.ItemCallback<ProductDTO> =
    object : DiffUtil.ItemCallback<ProductDTO>() {
        override fun areItemsTheSame(oldItem: ProductDTO, newItem: ProductDTO): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ProductDTO, newItem: ProductDTO): Boolean =
            oldItem == newItem
    }