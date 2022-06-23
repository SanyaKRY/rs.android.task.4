package com.example.animallist.ui.update

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
import com.example.animallist.databinding.FragmentUpdateBinding
import com.example.animallist.viewmodel.AnimalViewModel

class UpdateFragment : Fragment() {

    private val viewModel: AnimalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUpdateBinding.inflate(inflater)

        val args = UpdateFragmentArgs.fromBundle(requireArguments())

        binding.apply {
            updateEditName.setText(args.animal.name)
            updateEditAge.setText(args.animal.age.toString())
            updateEditBeauty.setText(args.animal.beauty)

            btnUpdate.setOnClickListener{
                if (TextUtils.isEmpty(updateEditName.text) or TextUtils.isEmpty(updateEditAge.text) or TextUtils.isEmpty(updateEditBeauty.text)) {
                    Toast.makeText(requireContext(), "It's empty:!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val name = updateEditName.text.toString()
                val age: Int = updateEditAge.text.toString().toInt()
                val beauty = updateEditBeauty.text.toString()
                val animal: Animal = Animal(args.animal.id, name, age, beauty, args.animal.timestamp)

                viewModel.update(animal)
                Toast.makeText(requireContext(), "Updated:!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateFragment_to_animalFragment)
            }
        }
        return binding.root
    }
}