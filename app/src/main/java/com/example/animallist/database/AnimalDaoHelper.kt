package com.example.animallist.database

import android.content.ContentValues
import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AnimalDaoHelper(val db: AnimalDatabaseHelper) : AnimalDao {

    override suspend fun insert(animal: Animal) {
        val cv: ContentValues = ContentValues()
        cv.put(COLUMN_ANIMAL_NAME, animal.name)
        cv.put(COLUMN_ANIMAL_AGE, animal.age)
        cv.put(COLUMN_ANIMAL_BEAUTY, animal.beauty)
        cv.put(COLUMN_ANIMAL_TIMESTAMP, animal.timestamp)
        val writableDb = db.writableDatabase
        writableDb.insert(TABLE_NAME, null, cv)
    }

    override suspend fun delete(animal: Animal) {
        val writableDb = db.writableDatabase
        writableDb.delete(TABLE_NAME, _ID + "=" + animal.id, null)
    }

    override suspend fun update(animal: Animal) {
        val cv: ContentValues = ContentValues()
        cv.put(COLUMN_ANIMAL_NAME, animal.name)
        cv.put(COLUMN_ANIMAL_AGE, animal.age)
        cv.put(COLUMN_ANIMAL_BEAUTY, animal.beauty)
        cv.put(COLUMN_ANIMAL_TIMESTAMP, animal.timestamp)
        val writableDb = db.writableDatabase
        writableDb.update(TABLE_NAME, cv, _ID + "=?", arrayOf(animal.id.toString()))
    }

    override suspend fun deleteAll() {
        val writableDb = db.writableDatabase
        writableDb.delete(TABLE_NAME, null, null)
    }

    override fun getAllAnimals(): LiveData<List<Animal>> {
        val readableDb = db.readableDatabase
        val cursor: Cursor = readableDb.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val listOfAnimals = mutableListOf<Animal>()
        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANIMAL_NAME))
                    val age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ANIMAL_AGE))
                    val beauty = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANIMAL_BEAUTY))
                    val timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ANIMAL_TIMESTAMP))
                    val animal = Animal(id, name, age, beauty, timestamp)
                    listOfAnimals.add(animal)
                } while (cursor.moveToNext())
            }
        }
        return MutableLiveData<List<Animal>>(listOfAnimals)
    }

    override fun getAllNameAnimals(): LiveData<List<Animal>> {
        val readableDb = db.readableDatabase
        val cursor: Cursor = readableDb.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_ANIMAL_NAME ASC", null)
        val listOfAnimals = mutableListOf<Animal>()
        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANIMAL_NAME))
                    val age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ANIMAL_AGE))
                    val beauty = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANIMAL_BEAUTY))
                    val timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ANIMAL_TIMESTAMP))
                    val animal = Animal(id, name, age, beauty, timestamp)
                    listOfAnimals.add(animal)
                } while (cursor.moveToNext())
            }
        }
        return MutableLiveData<List<Animal>>(listOfAnimals)
    }

    override fun getAllAgeAnimals(): LiveData<List<Animal>> {
        val readableDb = db.readableDatabase
        val cursor: Cursor = readableDb.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_ANIMAL_AGE ASC", null)
        val listOfAnimals = mutableListOf<Animal>()
        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANIMAL_NAME))
                    val age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ANIMAL_AGE))
                    val beauty = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANIMAL_BEAUTY))
                    val timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ANIMAL_TIMESTAMP))
                    val animal = Animal(id, name, age, beauty, timestamp)
                    listOfAnimals.add(animal)
                } while (cursor.moveToNext())
            }
        }
        return MutableLiveData<List<Animal>>(listOfAnimals)
    }

    override fun getAllBeautyAnimals(): LiveData<List<Animal>> {
        val readableDb = db.readableDatabase
        val cursor: Cursor = readableDb.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_ANIMAL_BEAUTY ASC", null)
        val listOfAnimals = mutableListOf<Animal>()
        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANIMAL_NAME))
                    val age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ANIMAL_AGE))
                    val beauty = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANIMAL_BEAUTY))
                    val timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ANIMAL_TIMESTAMP))
                    val animal = Animal(id, name, age, beauty, timestamp)
                    listOfAnimals.add(animal)
                } while (cursor.moveToNext())
            }
        }
        return MutableLiveData<List<Animal>>(listOfAnimals)
    }

    override fun searchDatabase(searchQuery: String): LiveData<List<Animal>> {
        val readableDb = db.readableDatabase
        val cursor: Cursor = readableDb.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ANIMAL_NAME like $searchQuery ORDER BY $COLUMN_ANIMAL_TIMESTAMP DESC", null)
        val listOfAnimals = mutableListOf<Animal>()
        cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANIMAL_NAME))
                    val age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ANIMAL_AGE))
                    val beauty = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANIMAL_BEAUTY))
                    val timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ANIMAL_TIMESTAMP))
                    val animal = Animal(id, name, age, beauty, timestamp)
                    listOfAnimals.add(animal)
                } while (cursor.moveToNext())
            }
        }
        return MutableLiveData<List<Animal>>(listOfAnimals)
    }
}