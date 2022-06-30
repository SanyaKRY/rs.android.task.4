package com.example.animallist.repository

import androidx.lifecycle.LiveData
import com.example.animallist.database.Animal
import com.example.animallist.database.AnimalDao
import com.example.animallist.database.AnimalDaoHelper

class AnimalRepository(val animalDao: AnimalDao) {

    suspend fun insert(animal: Animal) = animalDao?.insert(animal)

    suspend fun update(animal: Animal) = animalDao?.update(animal)

    suspend fun delete(animal: Animal) = animalDao?.delete(animal)

    suspend fun deleteAll() {
        animalDao?.deleteAll()
    }

    fun getAllAnimals(): LiveData<List<Animal>> = animalDao!!.getAllAnimals()

    fun getAllNameAnimals(): LiveData<List<Animal>> = animalDao!!.getAllNameAnimals()

    fun getAllAgeAnimals(): LiveData<List<Animal>> = animalDao!!.getAllAgeAnimals()

    fun getAllBeautyAnimals(): LiveData<List<Animal>> = animalDao!!.getAllBeautyAnimals()

    fun searchDatabase(searchQuery: String): LiveData<List<Animal>> {
        return animalDao!!.searchDatabase(searchQuery)
    }
}