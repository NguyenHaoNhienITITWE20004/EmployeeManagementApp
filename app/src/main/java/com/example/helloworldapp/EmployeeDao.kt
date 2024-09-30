package com.example.helloworldapp

import androidx.room.*

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employees")
    fun getAll(): List<Employee>

    @Insert
    fun insert(employee: Employee)

    @Update
    fun update(employee: Employee)

    @Delete
    fun delete(employee: Employee)
}
