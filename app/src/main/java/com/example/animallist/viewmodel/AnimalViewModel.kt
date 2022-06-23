package com.example.animallist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.animallist.database.Animal
import com.example.animallist.database.AnimalDatabase
import com.example.animallist.repository.AnimalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimalViewModel(application: Application) : AndroidViewModel(application) {

    private val animalDao = AnimalDatabase.getDatabase(application).animalDao()
    private val repository: AnimalRepository

    val getAllAnimals: LiveData<List<Animal>>
    val getAllNameAnimals: LiveData<List<Animal>>
    val getAllAgeAnimals: LiveData<List<Animal>>
    val getAllBeautyAnimals: LiveData<List<Animal>>

    init {
        repository = AnimalRepository(animalDao)
        getAllAnimals = repository.getAllAnimals()
        getAllNameAnimals = repository.getAllNameAnimals()
        getAllAgeAnimals = repository.getAllAgeAnimals()
        getAllBeautyAnimals = repository.getAllBeautyAnimals()
    }

    fun insert(animal: Animal) {
        viewModelScope.launch(Dispatchers.IO) { repository.insert(animal) }
    }

    fun delete(animal: Animal) {
        viewModelScope.launch(Dispatchers.IO) { repository.delete(animal) }
    }

    fun update(animal: Animal) {
        viewModelScope.launch(Dispatchers.IO) { repository.update(animal) }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) { repository.deleteAll() }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Animal>> {
        return repository.searchDatabase(searchQuery)
    }
}