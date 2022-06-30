package com.example.animallist.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val DB_NAME = "animal_database"
private const val DB_VERSION = 1
const val TABLE_NAME = "animal_table"
const val _ID = "id"
const val COLUMN_ANIMAL_NAME = "name"
const val COLUMN_ANIMAL_AGE = "age"
const val COLUMN_ANIMAL_BEAUTY = "beauty"
const val COLUMN_ANIMAL_TIMESTAMP = "timestamp"
private const val CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" + _ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_ANIMAL_NAME + " TEXT NOT NULL, " + COLUMN_ANIMAL_AGE + " INTEGER NOT NULL, " +
        COLUMN_ANIMAL_BEAUTY + " TEXT NOT NULL, " + COLUMN_ANIMAL_TIMESTAMP + "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
private const val UPGRADE_TABLE_SQL = "DROP TABLE IF EXIST " + TABLE_NAME


class AnimalDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {

        @Volatile
        private var INSTANCE: AnimalDatabaseHelper? = null

        fun getDatabase(context: Context): AnimalDatabaseHelper? {
            if (INSTANCE == null) {
                synchronized(AnimalDatabaseHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = AnimalDatabaseHelper(context)
                    }
                }
            }
            return INSTANCE
        }
    }

    fun getAnimalDaoHelper(): AnimalDaoHelper? {
        return INSTANCE?.let { AnimalDaoHelper(it) }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_SQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(UPGRADE_TABLE_SQL)
        onCreate(db)
    }
}