package mr.adkhambek.mvvm.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import mr.adkhambek.mvvm.R
import mr.adkhambek.mvvm.databinding.HomeFragmentBinding
import mr.adkhambek.mvvm.ui.home.adapter.ItemAdapter
import mr.adkhambek.mvvm.utils.Result

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {

    private lateinit var adapter: ItemAdapter
    private val viewModel: HomeViewModel by viewModels()
    private val binding: HomeFragmentBinding by viewBinding(HomeFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = ItemAdapter()
        viewModel
            .saveAndLoad()
            .onEach { result ->
                when (result) {
                    Result.Loading -> Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                    is Result.Error -> {
                        result.error.printStackTrace()
                    }
                    is Result.Success -> adapter.submitList(result.data)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        setupRecyclerView()
    }

    private fun setupRecyclerView() = with(binding.root) {
        layoutManager = LinearLayoutManager(context)
        this.adapter = this@HomeFragment.adapter
    }
}