package mr.adkhambek.mvvm.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import mr.adkhambek.mvvm.databinding.LayoutItemBinding
import mr.adkhambek.mvvm.model.ItemDTO

class ItemAdapter : ListAdapter<ItemDTO, ItemAdapter.ItemVH>(COMPARATOR) {

    private companion object COMPARATOR : DiffUtil.ItemCallback<ItemDTO>() {
        override fun areItemsTheSame(oldItem: ItemDTO, newItem: ItemDTO): Boolean = oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: ItemDTO, newItem: ItemDTO): Boolean = oldItem == newItem
    }

    inner class ItemVH(val binding: LayoutItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        val inflater = LayoutInflater.from(parent.context)
        return ItemVH(LayoutItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) = with(holder) {
        val item = getItem(absoluteAdapterPosition)

        binding.apply {
            nameTextView.text = item.name
            startDateTextView.text = item.startDate
            endDateTextView.text = item.endDate

            imageView.load(item.icon)
        }

        Unit
    }
}