package com.example.animallist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AnimalDao {

    @Insert
    suspend fun insert(animal: Animal)

    @Delete
    suspend fun delete(animal: Animal)

    @Update
    suspend fun update(animal: Animal)

    @Query("DELETE FROM animal_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM animal_table ORDER BY timestamp DESC")
    fun getAllAnimals(): LiveData<List<Animal>>

    @Query("SELECT * FROM animal_table ORDER BY name ASC")
    fun getAllNameAnimals(): LiveData<List<Animal>>

    @Query("SELECT * FROM animal_table ORDER BY age ASC")
    fun getAllAgeAnimals(): LiveData<List<Animal>>

    @Query("SELECT * FROM animal_table ORDER BY beauty ASC")
    fun getAllBeautyAnimals(): LiveData<List<Animal>>

    @Query("SELECT * FROM animal_table WHERE name like :searchQuery ORDER BY timestamp DESC")
    fun searchDatabase(searchQuery: String): LiveData<List<Animal>>
}