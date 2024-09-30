package com.example.helloworldapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employees")
data class Employee(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String,
    var phone: String,
    var email: String
)
