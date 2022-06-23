package com.example.animallist.ui.animal

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.animallist.R
import com.example.animallist.databinding.FragmentAnimalBinding
import com.example.animallist.viewmodel.AnimalViewModel
import com.google.android.material.snackbar.Snackbar

class AnimalFragment : Fragment() {

    private val viewModel: AnimalViewModel by viewModels()
    private lateinit var adapter: AnimalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentAnimalBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = AnimalAdapter(AnimalClickListener {
                animal -> findNavController().navigate(AnimalFragmentDirections.actionAnimalFragmentToUpdateFragment(animal)) })

        viewModel.getAllAnimals.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.apply {

            binding.recyclerView.adapter = adapter

            floatingActionButton.setOnClickListener{
                findNavController().navigate(R.id.action_animalFragment_to_addFragment)
            }
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val animal = adapter.currentList[position]
                viewModel.delete(animal)

                Snackbar.make(binding.root, "Deleted:!", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo"){
                        viewModel.insert(animal)
                    }
                    show()
                }
            }
        }).attachToRecyclerView(binding.recyclerView)

        setHasOptionsMenu(true)
        hideKeyboard(requireActivity())
        return binding.root
    }

    private fun hideKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = activity.currentFocus
        currentFocusedView.let {
            inputMethodManager.hideSoftInputFromWindow(
                currentFocusedView?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.animal_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    runQuery(newText)
                }
                return true
            }
        })
    }

    fun runQuery(query: String) {
        val searchQuery = "%$query%"
        viewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner, Observer { animals ->
            adapter.submitList(animals)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_sort_by_name -> viewModel.getAllNameAnimals.observe(viewLifecycleOwner, {
                animals -> adapter.submitList(animals)
            })
            R.id.action_delete_all -> deleteAllItems()
            R.id.action_sort -> findNavController().navigate(R.id.action_animalFragment_to_settingsFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val prefsSortValue = prefs.getString("pref_sort", "empty")
        when(prefsSortValue) {
            "name" -> viewModel.getAllNameAnimals.observe(viewLifecycleOwner, {
                    animals -> adapter.submitList(animals)
            })
            "age" -> viewModel.getAllAgeAnimals.observe(viewLifecycleOwner, {
                    animals -> adapter.submitList(animals)
            })
            "beauty" -> viewModel.getAllBeautyAnimals.observe(viewLifecycleOwner, {
                    animals -> adapter.submitList(animals)
            })
        }
        super.onResume()
    }

    private fun deleteAllItems() {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete All")
            .setMessage("Are you sure:?")
            .setPositiveButton("Yes"){
                dialog, _ -> viewModel.deleteAll()
                dialog.dismiss()
            }.setNegativeButton("No") { dialog, _ ->
                viewModel.deleteAll()
                dialog.dismiss()
            }.create().show()
    }
}