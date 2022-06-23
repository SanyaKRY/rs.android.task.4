package com.example.animallist.ui.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.animallist.R
import com.example.animallist.database.Animal
import com.example.animallist.databinding.FragmentAddBinding
import com.example.animallist.viewmodel.AnimalViewModel

class AddFragment : Fragment() {

    private val viewModel: AnimalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentAddBinding.inflate(inflater)

        binding.apply {
            btnAdd.setOnClickListener{
                if (TextUtils.isEmpty(editName.text) or TextUtils.isEmpty(editAge.text) or TextUtils.isEmpty(editBeauty.text)) {
                    Toast.makeText(requireContext(), "It's empty:!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val name = editName.text.toString()
                val age: Int = editAge.text.toString().toInt()
                val beauty = editBeauty.text.toString()
                val animal: Animal = Animal(0, name, age, beauty, System.currentTimeMillis())

                viewModel.insert(animal)
                Toast.makeText(requireContext(), "Successfully added:!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addFragment_to_animalFragment)
            }
        }

        return binding.root
    }
}