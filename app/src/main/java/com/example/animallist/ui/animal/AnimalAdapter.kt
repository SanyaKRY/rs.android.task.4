package com.example.animallist.ui.animal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animallist.database.Animal
import com.example.animallist.databinding.RowItemBinding

class AnimalAdapter(val clickListener: AnimalClickListener) : ListAdapter<Animal, AnimalAdapter.ViewHolder>(AnimalDiffCallback) {

    companion object AnimalDiffCallback : DiffUtil.ItemCallback<Animal>() {
        override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean = oldItem == newItem
    }

    class ViewHolder(val binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(animal: Animal, clickListener: AnimalClickListener) {
            binding.animal = animal
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, clickListener)
    }
}

class AnimalClickListener(val clickListener: (animal: Animal) -> Unit) {
    fun onClick(animal: Animal) = clickListener(animal)
}