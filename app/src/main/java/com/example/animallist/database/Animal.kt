package com.example.animallist.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "animal_table")
data class Animal(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var name: String,
    var age: Int,
    var beauty: String,
    var timestamp: Long
    ): Parcelable
