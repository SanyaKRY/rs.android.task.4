package com.example.animallist.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val DB_NAME = "animal_database"
private const val DB_VERSION = 1
private const val TABLE_NAME = "animal_table"
private const val _ID = "_id"
private const val COLUMN_ANIMAL_NAME = "name"
private const val COLUMN_ANIMAL_AGE = "age"
private const val COLUMN_ANIMAL_BEAUTY = "beauty"
private const val COLUMN_ANIMAL_TIMESTAMP = "timestamp"
private const val CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" + _ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_ANIMAL_NAME + " TEXT NOT NULL, " + COLUMN_ANIMAL_AGE + " INTEGER NOT NULL, " +
        COLUMN_ANIMAL_BEAUTY + " TEXT NOT NULL, " + COLUMN_ANIMAL_TIMESTAMP + "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
private const val UPGRADE_TABLE_SQL = "DROP TABLE IF EXIST " + TABLE_NAME


class AnimalDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_SQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(UPGRADE_TABLE_SQL)
        onCreate(db)
    }

    fun getAllAnimals(): Cursor {
        return readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun insert(animal: Animal) {
        val cv: ContentValues = ContentValues()
        cv.put(COLUMN_ANIMAL_NAME, animal.name)
        cv.put(COLUMN_ANIMAL_AGE, animal.age)
        cv.put(COLUMN_ANIMAL_BEAUTY, animal.beauty)
        cv.put(COLUMN_ANIMAL_TIMESTAMP, animal.timestamp)
        val db = writableDatabase
        db.insert(TABLE_NAME, null, cv)
    }

    fun update(animal: Animal) {
        val cv: ContentValues = ContentValues()
        cv.put(COLUMN_ANIMAL_NAME, animal.name)
        cv.put(COLUMN_ANIMAL_AGE, animal.age)
        cv.put(COLUMN_ANIMAL_BEAUTY, animal.beauty)
        cv.put(COLUMN_ANIMAL_TIMESTAMP, animal.timestamp)
        val db = writableDatabase
        db.update(TABLE_NAME, cv, _ID + "=?", arrayOf(animal.id.toString()))
    }

    fun delete(animal: Animal) {
        val db = writableDatabase
        db.delete(TABLE_NAME, _ID + "=?", arrayOf(animal.id.toString()))
    }

    fun deleteAll() {
        val db = writableDatabase
        db.delete(TABLE_NAME, null, null)
    }

    fun searchDatabase(searchQuery: String): Cursor {
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ANIMAL_NAME like $searchQuery ORDER BY $COLUMN_ANIMAL_TIMESTAMP DESC", null)
    }

    fun getAllNameAnimals(): Cursor {
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_ANIMAL_NAME ASC", null)
    }

    fun getAllAgeAnimals(): Cursor {
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_ANIMAL_AGE ASC", null)
    }

    fun getAllBeautyAnimals(): Cursor {
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_ANIMAL_BEAUTY ASC", null)
    }
}